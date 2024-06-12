import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Project } from '../../models/project';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrl: './create-project.component.css'
})
export class CreateProjectComponent {
    constructor(private fb:FormBuilder){}
    project:Project={};
    projectData = this.fb.group(
      {

      }
    )
}
