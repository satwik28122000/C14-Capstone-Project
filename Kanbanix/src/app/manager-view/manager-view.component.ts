import { Component } from '@angular/core';
import { Manager } from '../../models/manager';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-manager-view',
  templateUrl: './manager-view.component.html',
  styleUrl: './manager-view.component.css'
})
export class ManagerViewComponent {

  constructor(private routerService:RouterService){}
  
 manager:Manager = {
  managerId:"Sangeetha@123",
managerName:"Sangeetha Boopathy",
managerEmail:"sangeetha@gmail.com",
managerPassword:"qwerty",
projectList:[
  {
    projectId:"101",
    projectName:"Kanban Board",
    projectDesc:"Project Management System"
  },
  {
    projectId:"101",
    projectName:"Kanban Board",
    projectDesc:"Project Management System"
  },
  {
    projectId:"101",
    projectName:"Kanban Board",
    projectDesc:"Project Management System"
  },
  {
    projectId:"101",
    projectName:"Kanban Board",
    projectDesc:"Project Management System"
  },
]

 }
 onCreateProjectClick(){
  this.routerService.redirectToCreateProjectForm();
 }
}
