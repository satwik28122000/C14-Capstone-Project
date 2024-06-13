import { Component } from '@angular/core';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  constructor(private routerService:RouterService){}
  features =[
    {
      fIcon:"",
      fTitle:"Customized Access Level",
      fDesc:"Tailored permissions for each member, ensuring they have precisely the right level of access they need."
    },
    {
      fIcon:"",
      fTitle:"Task Handling",
      fDesc:"Define distinct roles for members, outlining their responsibilities and capabilities with precision."
    },
    {
      fIcon:"",
      fTitle:"Project Management",
      fDesc:"Take full control with our Project Module - create, edit, and search projects"
    },
    {
      fIcon:"",
      fTitle:"Highlight Tasks",
      fDesc:"Highlights the most important tasks with color-coded priority levels"
    }
  ]

  onManagerLoginClick(){
    this.routerService.redirectToManagerLogin();
  }
  onUserLoginClick(){
    this.routerService.redirectToUserLogin();
  }
}
