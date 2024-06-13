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
import { ManagerLoginComponent } from './manager-login/manager-login.component';
import { EmployeeLoginComponent } from './employee-login/employee-login.component';
import { CreateProjectComponent } from './create-project/create-project.component';

const routes: Routes = [
  { path: "", component: HomePageComponent },
  { path: "manager", component: ManagerLoginComponent },
  { path: "user", component: EmployeeLoginComponent },
  { path: "manager-register", component: ManagerRegisterComponent },
  { path: "user-register", component: EmployeeRegisterComponent },
  {
    path: "manager/:id", children: [
      { path: "project", component: ManagerViewComponent },
      { path: "project/:id", children:[
        { path: "",component:ProjectTaskComponent},
        { path:"project/:id/add-task",component:TaskregisterComponent}
      ]
      }
    ]
  },
  { path:"add-task",component:TaskregisterComponent},
  { path: "user/:id", component: UserViewComponent},
  { path:"add-project",component:CreateProjectComponent},
  { path: "**" ,component:PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
