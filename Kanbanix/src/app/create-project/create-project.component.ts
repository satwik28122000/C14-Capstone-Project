import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Project } from '../../Models/project';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';
import { ManagerService } from '../services/manager.service';
import { RouterService } from '../services/router.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrl: './create-project.component.css'
})
export class CreateProjectComponent implements CanComponentDeactivate{
  [x: string]: any;
    constructor(private fb:FormBuilder, 
      private managerService: ManagerService, 
      private routerService: RouterService,
      private location:Location){}
  
    project:Project={};
    projectForm = this.fb.group(
      {
        projectId: ['',[Validators.required,Validators.minLength(3)]],
        projectName: ['',[Validators.required,Validators.minLength(4)]],
        projectDesc: ['',[Validators.required,Validators.minLength(10)]],
        projectTasks: [null] 
      }
    )
    get projectId(){return this.projectForm.get('projectId')};
    get projectName(){return this.projectForm.get('projectName')};
    get projectDesc(){return this.projectForm.get('projectDesc')};

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
