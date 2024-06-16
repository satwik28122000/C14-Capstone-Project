import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  constructor(private r:Router) { }
  redirecttoHome()
  {
    this.r.navigate([""]);
  }

  redirectToManagerLogin() {
    this.r.navigate(['manager']);
  }

  redirectToUserLogin() {
    this.r.navigate(['user']);
  }

  redirectToManagerRegister() {
    this.r.navigate(['manager-register']);
  }

  redirectToUserRegister() {
    this.r.navigate(['user-register']);
  }

  redirectToManagerView(managerId: any) {
    this.r.navigate([`manager/${managerId}/project`]);
  }

  redirectToManagerProject(managerId: any, projectId: any) {
    this.r.navigate([`manager/${managerId}/project/${projectId}`]);
  }

  redirectToAddTask(managerId: any, projectId: any) {
    this.r.navigate([`manager/${managerId}/project/${projectId}/add-task`]);
  }

  redirectToUserView(userId: any) {
    this.r.navigate([`user/${userId}`]);
  }

  redirectToAddTaskDirectly(route:any) {
    this.r.navigate(['saveTask'],{ relativeTo: route });
  }

  redirectToPageNotFound() {
    this.r.navigate(['**']);
  }

  redirectToCreateProjectForm(){
    this.r.navigate(["add-project"])
  }
  redirectToPreviousPage(){
    this.r.navigate(["/"])
  }
  
}



