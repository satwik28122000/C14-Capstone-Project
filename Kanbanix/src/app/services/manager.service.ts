import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Manager } from '../../Models/Manager';
import { Task } from '../../Models/Task';



@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  constructor(private httpClient: HttpClient) { }
  kanbanUrl: string = "http://localhost:9000/api/kanban";
  authUrl: string = "http://localhost:9000/auth";

  //register manager
  registerManager(manager: Manager): Observable<Manager> {
    return this.httpClient.post<Manager>(`${this.kanbanUrl}/managers/saveManager`, manager);
  }

  //login manager
  loginManager(manager: Manager): Observable<any> {
    return this.httpClient.post<any>(`${this.authUrl}/loginManager`, manager);
  }

  //save project in manager project list
  saveManagerProject(project: any): Observable<any> {

    return this.httpClient.post<any>(`${this.kanbanUrl}/manager/saveProjectInManager`, project);
  }

  //will fetch all projects from manager   
  fetchAllProjects(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.kanbanUrl}/manager/projects`);
  }

  //fetch all manager
  fetchAllManager(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.kanbanUrl}/getAllManager`)
  }

  //will fetch all employee from employee document
  fetchAllEmployee(): Observable<any[]> {
    const token = localStorage.getItem('token');
    return this.httpClient.get<any[]>(`${this.kanbanUrl}/manager/getAllEmployee`);
  }

  //this will fetch project by id from project list of manager's project list
  fetchManagerProjectById(projectId: string): Observable<any> {
    return this.httpClient.get<any>(`${this.kanbanUrl}/manager/${projectId}`)
  }

  //this will save task in manager project and employee task list
  saveTaskInManagerProjectAndEmployee(projectId: string, task: Task): Observable<any> {
    return this.httpClient.post(`${this.kanbanUrl}/manager/project/${projectId}/saveTask`, task);
  }

  //this will update task in manager project and employee task list
  modifyTaskInProjectAndEmployee(projectId: string, task: Task): Observable<any> {
    return this.httpClient.put(`${this.kanbanUrl}/manager/updateTask/${projectId}`, task)
  }

  //fetch all projetcs from manager project list
  fetchAllProjectsFromManager(): Observable<any[]> {
    return this.httpClient.get<any>(`${this.kanbanUrl}/manager/projects`);
  }
}
