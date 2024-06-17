import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Project } from '../../Models/Project';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';
import { ManagerService } from '../services/manager.service';
import { RouterService } from '../services/router.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrl: './create-project.component.css'
})
export class CreateProjectComponent implements CanComponentDeactivate,OnInit{
  [x: string]: any;
    constructor(private fb:FormBuilder, 
      private managerService: ManagerService, 
      private routerService: RouterService,
      private location:Location,
    private activatedRoute:ActivatedRoute){}
    projectForm: FormGroup = new FormGroup({});
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      data =>{
        const managerId = data.get('id') ?? '';
        this.projectForm = this.fb.group(
          {
            projectId: ['',[Validators.required,Validators.minLength(3)]],
            projectName: ['',[Validators.required,Validators.minLength(4)]],
            projectDesc: ['',[Validators.required,Validators.minLength(10)]],
            managerId:[managerId],
            projectTasks: [null] 
          }
        )
      }
    )

    
  }
  
    project:Project={};
    
    get projectId(){return this.projectForm.get('projectId')};
    get projectName(){return this.projectForm.get('projectName')};
    get projectDesc(){return this.projectForm.get('projectDesc')};
    get managerId(){return this.projectForm.get('managerId')}

    onSubmit(){
      this.managerService.saveManagerProject(this.projectForm.value).subscribe({
      next:res=>{
        console.log(res);
        this.location.back();
      },
       error :err => {
        alert(err);
       }
      })
    }
    canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
      if (this.projectForm.dirty) {
          return confirm('You have unsaved changes! Do you really want to leave?');
      }
      return true;
  }
}
