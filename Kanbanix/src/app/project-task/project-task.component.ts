// src/app/project-task/project-task.component.ts
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { Project } from '../../Models/Project';
import { RouterService } from '../services/router.service';
import { ActivatedRoute } from '@angular/router';
import { ManagerService } from '../services/manager.service';

@Component({
  selector: 'app-project-task',
  templateUrl: './project-task.component.html',
  styleUrls: ['./project-task.component.css']
})
export class ProjectTaskComponent implements OnInit {
  project: Project = {}

  constructor(
    private fb: FormBuilder, 
    private routerService: RouterService, 
    private activatedRoute: ActivatedRoute,
    private managerService:ManagerService) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((data: any) => {
      console.log(data);
      const projectId = data.get('id');
      console.log(projectId);
      const managerToken = localStorage.getItem("token") ?? "";
      console.log(managerToken)
      this.managerService.fetchManagerProjectById(projectId).subscribe({
        next: (res:any) => {
          console.log(res);
          this.project=res;
        },
        error: err => {
          console.log(err);
        }
      })
    })
  }


  onAddTaskClick() {
    this.routerService.redirectToAddTaskDirectly(this.activatedRoute);
  }

}
