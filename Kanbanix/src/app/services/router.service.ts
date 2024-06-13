import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

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

  redirectToManagerView(managerId: number) {
    this.r.navigate([`manager/${managerId}/project`]);
  }

  redirectToManagerProject(managerId: number, projectId: number) {
    this.r.navigate([`manager/${managerId}/project/${projectId}`]);
  }

  redirectToAddTask(managerId: number, projectId: number) {
    this.r.navigate([`manager/${managerId}/project/${projectId}/add-task`]);
  }

  redirectToUserView(userId: number) {
    this.r.navigate([`user/${userId}`]);
  }

  redirectToAddTaskDirectly() {
    this.r.navigate(['add-task']);
  }

  redirectToPageNotFound() {
    this.r.navigate(['**']);
  }

  redirectToCreateProjectForm(){
    this.r.navigate(["add-project"])
  }

  
}



