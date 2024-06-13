import { Component } from '@angular/core';
import { Employee } from '../../models/employee';
import { Task } from '../../models/task';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.css'
})
export class UserViewComponent  implements CanComponentDeactivate{
 

   employee:Employee = {
    userId: 'emp001',
    userName: 'John Doe',
    password: 'password123',
    designation: 'Project Manager',
    emailId: 'johndoe@example.com',
    managerId: 'mgr001',
    userTaskList: [
        {
            taskId: 'task001',
            taskName: 'Design Homepage',
            taskDesc: 'Create the initial design for the homepage',
            status: 'In-Progress',
            priority: 'High',
            dueDate: '2024-06-20',
            assignedTo: {
                userId: 'emp001',
                userName: 'John Doe',
                password: 'password123',
                designation: 'Project Manager',
                emailId: 'johndoe@example.com',
                managerId: 'mgr001'
            },
            projectId: 'proj001'
        },
        {
            taskId: 'task002',
            taskName: 'Develop Login Module',
            taskDesc: 'Implement login functionality',
            status: 'Assigned',
            priority: 'Medium',
            dueDate: '2024-06-25',
            assignedTo: {
              userId: 'emp001',
              userName: 'John Doe',
              password: 'password123',
                designation: 'Developer',
                emailId: 'johndoe@example.com',
                managerId: 'emp001'
            },
            projectId: 'proj001'
        },
        {
            taskId: 'task003',
            taskName: 'Setup CI/CD Pipeline',
            taskDesc: 'Setup continuous integration and continuous deployment pipeline',
            status: 'Completed',
            priority: 'Low',
            dueDate: '2024-06-15',
            assignedTo: {
                userId: 'emp001',
                userName: 'John Doe',
                password: 'password123',
                designation: 'Project Manager',
                emailId: 'johndoe@example.com',
                managerId: 'mgr001'
            },
            projectId: 'proj001'
        },
        {
          taskId: 'task004',
          taskName: 'Design Homepage',
          taskDesc: 'Create the initial design for the homepage',
          status: 'In-Progress',
          priority: 'High',
          dueDate: '2024-06-20',
          assignedTo: {
              userId: 'emp001',
              userName: 'John Doe',
              password: 'password123',
              designation: 'Project Manager',
              emailId: 'johndoe@example.com',
              managerId: 'mgr001'
          },
          projectId: 'proj001'
      },
      {
          taskId: 'task005',
          taskName: 'Develop Login Module',
          taskDesc: 'Implement login functionality',
          status: 'Assigned',
          priority: 'Medium',
          dueDate: '2024-06-25',
          assignedTo: {
            userId: 'emp001',
            userName: 'John Doe',
            password: 'password123',
              designation: 'Developer',
              emailId: 'johndoe@example.com',
              managerId: 'emp001'
          },
          projectId: 'proj001'
      },
      {
          taskId: 'task006',
          taskName: 'Setup CI/CD Pipeline',
          taskDesc: 'Setup continuous integration and continuous deployment pipeline',
          status: 'Completed',
          priority: 'Low',
          dueDate: '2024-06-15',
          assignedTo: {
              userId: 'emp001',
              userName: 'John Doe',
              password: 'password123',
              designation: 'Project Manager',
              emailId: 'johndoe@example.com',
              managerId: 'mgr001'
          },
          projectId: 'proj001'
      }
    ]
};
    currentItem:any={};
  assignedList = this.employee.userTaskList?.filter(t => (t.status == 'Assigned' || t.status == 'assigned'));
  inProgressList = this.employee.userTaskList?.filter(t => (t.status == 'In-Progress' || t.status == 'In-progress'));
  completedList = this.employee.userTaskList?.filter(t => (t.status == 'Completed' || t.status == 'completed'));

  onDrag(task:any){
    console.log(task);
    return this.currentItem = task;
  }
  onDrop(event:any,status:string){
    event.preventDefault();
    const record = this.employee.userTaskList?.find( t => t.taskId == this.currentItem?.taskId);
    console.log(record);
    if(record != undefined){
        record.status=status;
    }
    this.assignedList = this.employee.userTaskList?.filter(t => (t.status == 'Assigned' || t.status == 'assigned'));
    this.inProgressList = this.employee.userTaskList?.filter(t => (t.status == 'In-Progress' || t.status == 'In-progress'));
    this.completedList = this.employee.userTaskList?.filter(t => (t.status == 'Completed' || t.status == 'completed'));
  
    this.currentItem=null;
  }
  onDragOver(event:any){
    event.preventDefault();
}
canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
  return confirm("Do you want to discard your changes?");
}
}

