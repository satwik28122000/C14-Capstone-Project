import { Component, Input } from '@angular/core';
import { Task } from '../../Models/Task';

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrl: './task-card.component.css'
})
export class TaskCardComponent {

  @Input() task:Task ={}
}
