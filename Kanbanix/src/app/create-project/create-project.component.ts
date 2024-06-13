import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Project } from '../../models/project';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrl: './create-project.component.css'
})
export class CreateProjectComponent implements CanComponentDeactivate{
    constructor(private fb:FormBuilder){}
  
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


    onSubmit(){}
    canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
      if (this.projectForm.dirty) {
          return confirm('You have unsaved changes! Do you really want to leave?');
      }
      return true;
  }
}
