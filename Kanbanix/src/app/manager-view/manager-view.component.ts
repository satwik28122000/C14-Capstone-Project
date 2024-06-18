import { Component, OnInit } from '@angular/core';
import { Manager } from '../../Models/Manager';
import { RouterService } from '../services/router.service';
import { ManagerService } from '../services/manager.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-manager-view',
  templateUrl: './manager-view.component.html',
  styleUrl: './manager-view.component.css'
})
export class ManagerViewComponent implements OnInit {

  constructor(private routerService:RouterService,private managerService:ManagerService,
    private activatedRoute:ActivatedRoute, private location:Location
  ){}

  manager:Manager = {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: data => {
        const managerId = data.get('id') ?? "";
        this.managerService.fetchAllManager().subscribe({
          next: (res:any) =>{
            console.log(res);
            this.manager = res.find((m: { managerId: string; }) => m.managerId === managerId);
            if(this.manager){
              alert("Welcome to "+this.manager.managerName+"'s Workspace")
            }
            else{
              alert("Manager not found");
              this.routerService.redirectToPreviousPage();
            }
            console.log(this.manager);
            
          },
          error: err => {
            alert(err); 
          }
        })
      }
    })
  }
  

 onCreateProjectClick(){
  this.routerService.redirectToCreateProjectForm(this.manager?.managerId,this.activatedRoute);
 }
}
