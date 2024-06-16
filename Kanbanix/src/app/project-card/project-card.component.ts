import { Component, Input } from '@angular/core';
import { Project } from '../../Models/Project';
import { Manager } from '../../Models/Manager';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrl: './project-card.component.css'
})
export class ProjectCardComponent {

  @Input() project:Project = {}

}
