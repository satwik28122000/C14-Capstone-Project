import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Task } from '../../models/task';

@Component({
  selector: 'app-taskregister',
  templateUrl: './taskregister.component.html',
  styleUrls: ['./taskregister.component.css']
})
export class TaskregisterComponent implements OnInit {
  registrationForm: FormGroup = new FormGroup({});
  
  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      taskId: [''],
      taskName: ['', [Validators.required, Validators.minLength(3), Validators.pattern(/^[A-Za-z\s]+$/)]],
      taskDesc: ['', [Validators.required]],
      status: ['Assigned', [Validators.required]],
      priority: ['', [Validators.required]],
      dueDate: ['', [Validators.required]],
      assignedTo: ['', [Validators.required]],
      projectId: ['', [Validators.required]]
    });
  }

  onSubmit() {
    console.log(this.registrationForm.valid);
    console.log(this.registrationForm.value);
    if (this.registrationForm.valid) {
      const task: Task = {
        taskId: this.registrationForm.value.taskId,
        taskName: this.registrationForm.value.taskName,
        taskDesc: this.registrationForm.value.taskDesc,
        status: this.registrationForm.value.status,
        priority: this.registrationForm.value.priority,
        dueDate: this.registrationForm.value.dueDate,
        assignedTo: this.registrationForm.value.assignedTo,
        projectId: this.registrationForm.value.projectId,
      };
      // Handle the task object, e.g., send it to a server
      console.log(task);
    }
  }
  onClear() {
    this.registrationForm.reset();
  }
}
