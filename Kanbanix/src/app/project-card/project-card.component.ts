import { Component, Input } from '@angular/core';
import { Project } from '../../models/project';
import { Manager } from '../../models/manager';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrl: './project-card.component.css'
})
export class ProjectCardComponent {

  @Input() project:Project = {}

}
