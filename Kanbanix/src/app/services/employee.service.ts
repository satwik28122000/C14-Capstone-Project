import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../../models/employee';
import { Task } from '../../models/task';

@Injectable({
  providedIn: 'root'
})


export class ManagerService {

 



  constructor(private http:HttpClient) { }

  registerEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>('http://localhost:8081/registerEmployee', employee);
  }

  getAllEmployee(): Observable<Employee[]> {
    return this.http.get<Employee[]>('http://localhost:8081/getAllEmployee');
  }

  getEmployeeByUserId(userId: string): Observable<Employee> {
    return this.http.get<Employee>('http://localhost:8081/getEmployeeByUserId/' + userId);
  }

  updateEmployeeTaskInTaskList(userId: string, employee: Task): Observable<Employee> {
    return this.http.put<Employee>('http://localhost:8081/updateEmployeeTaskInTaskList/' + userId, employee);
  }

  saveEmployeeTaskToTaskList(userId: string, task: Task): Observable<Employee>{
    return this.http.post<Employee>('http://localhost:8081/saveEmployeeTaskToTaskList/' + userId, task);
  }

  deleteTaskFromEmployee(userId: string, taskId: string): Observable<Employee>{
    return this.http.delete<Employee>('http://localhost:8081/deleteTaskFromEmployee/' + userId + '/' + taskId);
  }

  getAllEmployeeTaskFromTaskList(userId: string): Observable<Employee>{
    return this.http.get<Employee>('http://localhost:8081/getAllEmployeeTaskFromTaskList/' + userId);
  }

  

}
