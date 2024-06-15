import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { Project } from '../../models/project';
import { Task } from '../../models/task';
import { Manager } from '../../models/manager';



@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  constructor(private httpClient:HttpClient) { }
    kanbanUrl:string = "http://localhost:9000/api/kanban";
    authUrl:string ="http://localhost:9000/auth";

  //register manager
   registerManager(manager:Manager):Observable<Manager>{
      return this.httpClient.post<Manager>(`${this.kanbanUrl}/managers/saveManager`,manager);
   }

   //login manager
   loginManager(manager:Manager):Observable<any>{
      return this.httpClient.post<any>(`${this.authUrl}/loginManager`,manager);
   }

  //save project in manager project list
   saveManagerProject(project:any):Observable<any>{
    const token = localStorage.getItem('token');

    // Check if token exists
    if (token) {
      // Create HTTP headers with the token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      return this.httpClient.post<any>(`${this.kanbanUrl}/manager/saveProjectInManager`,project,{headers});
      }
      else{
         throw new Error("Authentication token not found")
      }
      
   }

  //will fetch all projects from manager   
   fetchAllProjects():Observable<any[]>{
      return this.httpClient.get<any[]>(`${this.kanbanUrl}/manager/projects`);
   }

   //fetch all manager
   fetchAllManager():Observable<any[]>{
      return this.httpClient.get<any[]>(`${this.kanbanUrl}/getAllManager`)
   }

   //will fetch all employee from employee document
   fetchAllEmployee():Observable<any[]>{
    const token = localStorage.getItem('token');
     // Check if token exists
     if (token) {
      // Create HTTP headers with the token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
      return this.httpClient.get<any[]>(`${this.kanbanUrl}/manager/getAllEmployee`);
      }
      else{
         throw new Error("Authentication token not found")
      }
    }

   //this will fetch project by id from project list of manager's project list
   fetchManagerProjectById(projectId:string):Observable<any>{
      const token = localStorage.getItem('token');

    // Check if token exists
    if (token) {
      // Create HTTP headers with the token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      return this.httpClient.get<any>(`${this.kanbanUrl}/manager/${projectId}`,{headers})
      }
      else{
         throw new Error("Authentication token not found")
      }
   }

   //this will save task in manager project and employee task list
   saveTaskInManagerProjectAndEmployee(projectId:string,task:Task):Observable<any>{
    const token = localStorage.getItem('token');

    if(token){
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
      return this.httpClient.post(`${this.kanbanUrl}/manager/project/${projectId}/saveTask`,task,{headers});
    }
    else{
      throw new Error("Not Authorized")
    }
   }

   //this will update task in manager project and employee task list
   modifyTaskInProjectAndEmployee(projectId:string,task:Task):Observable<any>{
    return this.httpClient.put(`${this.kanbanUrl}/manager/updateTask/${projectId}`,task)
   }

   //fetch all projetcs from manager project list
   fetchAllProjectsFromManager():Observable<any[]>{
      const token = localStorage.getItem('token');

    // Check if token exists
    if (token) {
      // Create HTTP headers with the token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      // Make the GET request with the headers
      return this.httpClient.get<any>(`${this.kanbanUrl}/manager/projects`,{headers});
    }else {
      // Handle the case where the token is missing
      throw new Error('Authentication token not found');
    } 
   }
}
