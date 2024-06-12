import { Component } from '@angular/core';
import { Manager } from '../../models/manager';
import { Project } from '../../models/project';

@Component({
  selector: 'app-manager-view',
  templateUrl: './manager-view.component.html',
  styleUrl: './manager-view.component.css'
})
export class ManagerViewComponent {

  
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
//  searchText:string="";
//   filteredProjects:any;
//  searchProjectByName():any{
//   if(!this.searchText || this.searchText == ""){
//     return this.manager.projectList;
//   }
//   else{
//     console.log(this.searchText);
    
//     return this.manager.projectList?.filter(p => p.projectName?.includes(this.searchText));
//   }
//  }
}
