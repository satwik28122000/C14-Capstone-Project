import { Component } from '@angular/core';

import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';
import { EmployeeService } from '../services/employee.service';
import { RouterService } from '../services/router.service';
import { ActivatedRoute } from '@angular/router';
import { Employee } from '../../Models/Employee';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements CanComponentDeactivate {
  employee: Employee = {
    userId: 'emp001',
    userName: 'John Doe',
    password: 'password123',
    designation: 'Project Manager',
    emailId: 'johndoe@example.com',
    managerId: 'mgr001',
    userTaskList: [
      // Your task list data here
    ]
  };
  
  currentItem: any = {};
  assignedList = this.employee.userTaskList?.filter(t => (t.status === 'Assigned' || t.status === 'assigned'));
  inProgressList = this.employee.userTaskList?.filter(t => (t.status === 'In-Progress' || t.status === 'In-progress'));
  completedList = this.employee.userTaskList?.filter(t => (t.status === 'Completed' || t.status === 'completed'));

  constructor(
    private routerService: RouterService,
    private employeeService: EmployeeService,
    private activatedRoute: ActivatedRoute
  ) { }

  onDrag(task: any) {
    console.log(task);
    this.currentItem = task;
  }

  onDrop(event: any, status: string) {
    event.preventDefault();
    const record = this.employee.userTaskList?.find(t => t.taskId === this.currentItem?.taskId);
    console.log(record);
    if (record !== undefined) {
      record.status = status;
    }
    this.updateTaskLists();
    this.currentItem = null;
  }

  onDragOver(event: any) {
    event.preventDefault();
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: data => {
        const employeeId = data.get('id') ?? "";
        this.employeeService.getEmployeeByUserId(employeeId).subscribe({
          next: (res: any) => {
            console.log(res);
            this.employee = res;  
            localStorage.setItem("Token", res.Token);  
            console.log(this.employee);
          },
          error: (err: any) => {
            console.log(err);
          }
        });
      }
    });
  }

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    return confirm("Do you want to discard your changes?");
  }

  private updateTaskLists() {
    this.assignedList = this.employee.userTaskList?.filter(t => (t.status === 'Assigned' || t.status === 'assigned'));
    this.inProgressList = this.employee.userTaskList?.filter(t => (t.status === 'In-Progress' || t.status === 'In-progress'));
    this.completedList = this.employee.userTaskList?.filter(t => (t.status === 'Completed' || t.status === 'completed'));
  }
}
