import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Observable } from 'rxjs';
import moment from 'moment';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { ManagerService } from '../services/manager.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
@Component({
  selector: 'app-taskregister',
  templateUrl: './taskregister.component.html',
  styleUrls: ['./taskregister.component.css']
})
export class TaskregisterComponent implements OnInit, CanComponentDeactivate {
  registrationForm: FormGroup = new FormGroup({});
  empList: any[] = [];
  filteredList: any[] = [];
  constructor(
    private formBuilder: FormBuilder, 
    private managerService: ManagerService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {}
  ngOnInit(): void {
    this.managerService.fetchAllEmployee().subscribe(res => {
      this.empList = res;
      this.filteredList = this.empList?.filter((emp: any) => {
        let inProgressList: any[] = emp.userTaskList?.filter((task: any) => task.status == "In-Progress");
        let assignedList: any[] = emp.userTaskList?.filter((task: any) => task.status == "Assigned");
        return (inProgressList?.length < 3 && assignedList?.length < 3 || emp.userTaskList == null || inProgressList == null || assignedList == null);
      });
      console.log(res);
      console.log(this.filteredList);
      if (!this.filteredList || this.filteredList == null) {
        this.filteredList = ['Employees already have tasks >= 3'];
      }
    });
    this.activatedRoute.paramMap.subscribe(data => {
      console.log(data);
      const pId = data.get('id') ?? "";
      this.registrationForm = this.formBuilder.group({
        taskId: ['', [Validators.required, Validators.minLength(3)]],
        taskName: ['', [Validators.required, Validators.minLength(3), Validators.pattern(/^[A-Za-z\s]+$/)]],
        taskDesc: ['', [Validators.required]],
        status: ['Assigned', [Validators.required]],
        priority: ['', [Validators.required]],
        dueDate: ['', [Validators.required, this.dateValidator]], 
        assignedTo: [null, [Validators.required]],
        projectId: [pId]
      });
    });
  }
  onSubmit() {
    console.log(this.registrationForm.value);
    this.managerService.saveTaskInManagerProjectAndEmployee(this.registrationForm.value.projectId, this.registrationForm.value).subscribe({
      next: res => {
        console.log(res);
        this.registrationForm.markAsPristine(); // Mark the form as pristine after successful submission
        this.location.back(); // Navigate back after successful submission
      },
      error: err => {
        console.log(err);
      }
    });
  }
  onClear() {
    this.registrationForm.reset();
  }
  dateValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const enteredDate = moment(control.value, 'YYYY-MM-DD', true);
    const todaysDate = moment().startOf('day');
    if (!enteredDate.isValid() || enteredDate.isBefore(todaysDate, 'day')) {
      return { 'dateError1': true };
    }
    return null;
  }
  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (this.registrationForm.dirty) {
      return confirm("You have unsaved changes. Do you really want to leave?");
    }
    return true;
  }
}