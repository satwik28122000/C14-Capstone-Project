import { Component, OnInit } from '@angular/core';
import { Manager } from '../../models/manager';
import { RouterService } from '../services/router.service';
import { ManagerService } from '../services/manager.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-manager-view',
  templateUrl: './manager-view.component.html',
  styleUrl: './manager-view.component.css'
})
export class ManagerViewComponent implements OnInit {

  constructor(private routerService:RouterService,private managerService:ManagerService,
    private activatedRoute:ActivatedRoute
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
            localStorage.setItem("Token",res.Token)
            console.log(this.manager);
            
          },
          error: err => {
            console.log(err); 
          }
        })
      }
    })
  }
  

 onCreateProjectClick(){
  this.routerService.redirectToCreateProjectForm();
 }
}
