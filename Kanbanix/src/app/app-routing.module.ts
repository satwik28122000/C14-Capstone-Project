// src/app/app-routing.module.ts
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
import { DeactiveAuthGuard } from './guard/deactive-auth.guard';

import { AuthGuard } from './guard/auth.guard';
import { AuthService } from './services/auth.service'; // Import AuthService
import { ChildAuthGuard } from './guard/child-auths.guard';

const routes: Routes = [
  { path: "", component: HomePageComponent },
  { path: "manager", component: ManagerLoginComponent },
  { path: "user", component: EmployeeLoginComponent },
  { path: "managerRegister", component: ManagerRegisterComponent },
  { path: "user-register", component: EmployeeRegisterComponent },
  { 
    path: "logout", 
    canActivate: [AuthGuard], // Optional: Secure logout route if needed
    component: HomePageComponent, // Example: Navigate to home after logout
    resolve: { 
      logout: AuthService // Use resolver to trigger logout
    } 
  },
  {
    path: "manager/:id",
    canActivate: [AuthGuard],
    canActivateChild: [ChildAuthGuard],
    children: [
      { path: "project", component: ManagerViewComponent },
      { path: "add-project", component: CreateProjectComponent, canDeactivate: [DeactiveAuthGuard] },
      {
        path: "project/:id",
        children: [
          { path: "", component: ProjectTaskComponent },
          { path: "saveTask", component: TaskregisterComponent, canDeactivate: [DeactiveAuthGuard] }
        ]
      }
    ]
  },
  { path: "user/:id", component: UserViewComponent },
  { path: "**", component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
