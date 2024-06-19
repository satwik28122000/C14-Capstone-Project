import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Task } from '../../Models/Task';
import { Observable } from 'rxjs';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
 import moment from 'moment';
import { ManagerService } from '../services/manager.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Employee } from '../../Models/Employee';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
//import * as moment from 'moment-timezone';

@Component({
  selector: 'app-taskregister',
  templateUrl: './taskregister.component.html',
  styleUrls: ['./taskregister.component.css']
})
export class TaskregisterComponent implements OnInit,CanComponentDeactivate {
  registrationForm: FormGroup = new FormGroup({});
  
  constructor(private formBuilder: FormBuilder, 
    private  managerService:ManagerService,
    private activatedRoute:ActivatedRoute,
    private location:Location) {}
    empList:any[] = [];
    
    filteredList:any[]=[];
    
  ngOnInit(): void {
    this.managerService.fetchAllEmployee().subscribe(
      res =>{
        this.empList=res;
        this.filteredList = this.empList?.filter((emp:any) =>{
          let inProgressList:[] = emp.userTaskList?.filter( (task:any) => task.status == "In-Progress");
          let assignedList:[] = emp.userTaskList?.filter( (task:any) => task.status == "Assigned");
          return (inProgressList?.length <3 && assignedList?.length<3 || emp.userTaskList==null || inProgressList==null 
            || assignedList==null); 
        });
        console.log(res);
        console.log(this.filteredList);
        if(!this.filteredList || this.filteredList==null){
          this.filteredList=['Employees already have tasks >= 3']
        }
      }
    )
    this.activatedRoute.paramMap.subscribe(data => {
      console.log(data);
      const pId = data.get('id') ?? "";
      this.registrationForm = this.formBuilder.group({
        taskId: ['',[Validators.required, Validators.minLength(3)]],
        taskName: ['', [Validators.required, Validators.minLength(3), Validators.pattern(/^[A-Za-z\s]+$/)]],
        taskDesc: ['', [Validators.required]],
        status: ['Assigned', [Validators.required]],
        priority: ['', [Validators.required]],
        dueDate: ['', [Validators.required, this.dateValidator]], 
        assignedTo: [null, [Validators.required]],
        projectId: [pId]
      });

    })
    
  }
    

  selectedDate: string="";

  onDateChange(event: MatDatepickerInputEvent<Date>) {
    const date = event.value;

    if (date) {
      // Convert the date to a string in the format YYYY-MM-DD
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      this.selectedDate = `${year}-${month}-${day}`;
      console.log(this.selectedDate);
      this.registrationForm.value.dueDate = this.selectedDate;
    }
    return this.selectedDate;
  }


  onSubmit() {
    console.log(this.registrationForm.value)
    this.managerService.saveTaskInManagerProjectAndEmployee(this.registrationForm.value.projectId,this.registrationForm.value).subscribe(
      {
        next: res => {
          console.log(res);
          this.location.back();

        },
        error: err => {
          console.log(err);
        }
      }
    )
  }
  
  onClear() {
    this.registrationForm.reset();
  }
  dateValidator(control: AbstractControl): { [key: string]: boolean } | null {
    // const enterDate = moment(control.value);
    // const todaysDate = moment();

 // if (enterDate.isBefore(todaysDate, 'day')) {
    //   return { 'dateError1': true }; 
    // }
    //return null;


    const enteredDate = moment(control.value, 'YYYY-MM-DD', true);
  const todaysDate = moment().startOf('day');

  if (!enteredDate.isValid() || enteredDate.isBefore(todaysDate, 'day')) {
    return { 'dateError1': true };
  }
  return null;


  }


canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    return confirm("Do you want to discard your changes?");
  }
}



