import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../../Models/Employee';
import { Task } from '../../Models/Task';

@Injectable({
  providedIn: 'root'
})


export class EmployeeService {

  constructor(private http:HttpClient) { }
  kanbanUrl:string = "http://localhost:9000/api/kanban";
  authUrl:string = "http://localhost:9000/auth";

  registerEmployee(employee: Employee): Observable<any> {
    return this.http.post<any>(`${this.kanbanUrl}/register`, employee);
  }

  loginEmployee(employee: Employee): Observable<any> {
    return this.http.post<any>(`${this.authUrl}/login`, employee);
  }

  getEmployeeByUserId(): Observable<any> {
    return this.http.get<any>(`${this.kanbanUrl}/user/getEmployeesByUserId`);
  }

  getAllEmployeeTaskFromTaskList(): Observable<any>{
    return this.http.get<any>(`${this.kanbanUrl}/user/employees/tasks`);
  }

  getTaskByIdFromEmployee(taskId: string): Observable<any>{
    return this.http.get<any>(`${this.kanbanUrl}/user/findTaskByIdFromEmployee/task/${taskId}`);
  }

  updateTaskFromEmployeeToManager(task: Task): Observable<any> {
    return this.http.put<any>(`${this.kanbanUrl}/user/updateEmployeeTask`, task);
  }
  
}



// public ResponseEntity<?> getAllEmployeeTaskFromTaskList(@PathVariable String userId) throws EmployeeNotFoundException   done
// public ResponseEntity<?> fetchTaskByIdFromEmployee(@PathVariable String taskId,HttpServletRequest request) throws TaskNotFoundException , EmployeeNotFoundException
// public ResponseEntity<?> modifyTaskInEmployeeToProject(HttpServletRequest request,@RequestBody Task task) throws ProjectNotFoundException,
