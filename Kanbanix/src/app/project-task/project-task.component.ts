// src/app/project-task/project-task.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { Project } from '../../models/project';

@Component({
  selector: 'app-project-task',
  templateUrl: './project-task.component.html',
  styleUrls: ['./project-task.component.css']
})
export class ProjectTaskComponent implements OnInit {

  projectForm: FormGroup;
  project:Project = {
    projectId:"1001",
    projectName:"Travel App",
    projectDesc:"Your travel guide",
    projectTasks:[
      {
        taskId:"1",
        taskName:"Auth Service",
        status:"Assigned",
        taskDesc: "Create auth service domains",
        priority: "High",
        dueDate: "12-06-2024",
        assignedTo: {
          userId:"Priyanka@123"
        }
      },
      {
        taskId:"1",
        taskName:"Auth Service",
        status:"Assigned",
        taskDesc: "Create auth service domains",
        priority: "High",
        dueDate: "12-06-2024",
        assignedTo: {
          userId:"Priyanka@123"
        }
      },
      {
        taskId:"1",
        taskName:"Auth Service",
        status:"Assigned",
        taskDesc: "Create auth service domains",
        priority: "High",
        dueDate: "12-06-2024",
        assignedTo: {
          userId:"Priyanka@123"
        }
      }
    ]
  }
  constructor(private fb: FormBuilder) {
    this.projectForm = this.fb.group({
      tasks: this.fb.array([]) 
    });
  }

  ngOnInit(): void {}

  get tasks(): FormArray {
    return this.projectForm.get('tasks') as FormArray;
  }

  addTask(): void {
    this.tasks.push(this.fb.group({
      taskName: '',
      taskDesc: '',
      status: '',
      priority: '',
      dueDate: '',
      assignedTo: '',
      isEditing: false
    }));
  }

  editTask(index: number): void {
    this.tasks.at(index).get('isEditing')?.setValue(true);
  }

  saveTask(index: number): void {
    this.tasks.at(index).get('isEditing')?.setValue(false);
  }

  onSubmit(): void {
    console.log(this.projectForm.value);
  }
}
