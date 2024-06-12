import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { ManagerRegisterComponent } from './manager-register/manager-register.component';
import { EmployeeRegisterComponent } from './employee-register/employee-register.component';
import { ManagerViewComponent } from './manager-view/manager-view.component';
import { UserViewComponent } from './user-view/user-view.component';
import { ProjectTaskComponent } from './project-task/project-task.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { TaskregisterComponent } from './taskregister/taskregister.component';

const routes: Routes = [
  { path: "", component: HomePageComponent },
  { path: "manager", component: HomePageComponent },
  { path: "user", component: HomePageComponent },
  { path: "manager-register", component: ManagerRegisterComponent },
  { path: "user-register", component: EmployeeRegisterComponent },
  {
    path: "manager/:id", children: [
      { path: "project", component: ManagerViewComponent },
      { path: "project/:id", children:[
        { path: "project/:id",component:ProjectTaskComponent},
        { path:"project/:id/add-task",component:TaskregisterComponent}
      ]
      }
    ]
  },
  { path:"add-task",component:TaskregisterComponent},
  { path: "user/:id", component: UserViewComponent},
  { path: "**" ,component:PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
