import { Component } from '@angular/core';

import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';
import { EmployeeService } from '../services/employee.service';
import { RouterService } from '../services/router.service';
import { ActivatedRoute } from '@angular/router';
import { Employee } from '../../Models/Employee';
import { NgStyle } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements CanComponentDeactivate {
  employee: Employee = {};
  assignedList:any=[];
  inProgressList:any=[];
  completedList:any=[];
  
  currentItem: any = {};
  
  constructor(
    private routerService: RouterService,
    private employeeService: EmployeeService,
    private activatedRoute: ActivatedRoute,
    private snackBar:MatSnackBar
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: data => {
        const employeeId = data.get('id') ?? "";
        this.employeeService.getEmployeeByUserId().subscribe({
          next: (res: any) => {
            console.log(res);
            this.employee = res;  
            console.log(this.employee);
            this.assignedList= this.employee.userTaskList?.filter(t => (t.status === 'Assigned' || t.status === 'assigned'));
            this.inProgressList = this.employee.userTaskList?.filter(t => (t.status === 'In-Progress' || t.status === 'In-progress'));
            this.completedList = this.employee.userTaskList?.filter(t => (t.status === 'Completed' || t.status === 'completed'));

          },
          error: (err: any) => {
            console.log(err);
          }
        });
      }
    });
  }

  onDrag(task: any) {
    const token:string= localStorage.getItem('token') ?? '';
    localStorage.setItem('token',token)
    console.log(task);
    this.currentItem = task;
  }

  onDrop(event: any, status: string) {
    event.preventDefault();
    const record:any = this.employee.userTaskList?.find(t => t.taskId === this.currentItem?.taskId);
    console.log("record"+record);
    if (record !== undefined) {
      record.status = status;
    }
    this.employeeService.updateTaskFromEmployeeToManager(record).subscribe({
      next : (res:any) =>{
        console.log(res);
        
        this.snackBar.open("Data updated","x")
      },
      error : (err:any)=>{
          console.log(err);
      }
    })
    
    this.updateTaskLists();
    this.currentItem = null;
  }

  onDragOver(event: any) {
    event.preventDefault();
  }

  

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    return confirm("Do you want to discard your changes?");
  }

  updateTaskLists() {
    this.assignedList = this.employee.userTaskList?.filter(t => (t.status === 'Assigned' || t.status === 'assigned'));
    this.inProgressList = this.employee.userTaskList?.filter(t => (t.status === 'In-Progress' || t.status === 'In-progress'));
    this.completedList = this.employee.userTaskList?.filter(t => (t.status === 'Completed' || t.status === 'completed'));
  }
}
