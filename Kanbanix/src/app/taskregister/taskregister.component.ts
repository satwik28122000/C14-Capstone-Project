import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Task } from '../../models/task';
import { Observable } from 'rxjs';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import moment from 'moment';
import { ManagerService } from '../services/manager.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

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

  ngOnInit(): void {
    this.managerService.fetchAllEmployee().subscribe(
      res =>{
        this.empList=res;
        console.log(res);
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
    const enterDate = moment(control.value);
    const todaysDate = moment();

    if (enterDate.isBefore(todaysDate, 'day')) {
      return { 'dateError1': true }; 
    }
    return null;
  }
  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    return confirm("Do you want to discard your changes?");
  }
}
