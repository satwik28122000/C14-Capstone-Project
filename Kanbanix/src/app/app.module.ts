import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms'; 

import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { NavbarComponent } from './navbar/navbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FooterComponent } from './footer/footer.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ManagerViewComponent } from './manager-view/manager-view.component';
import { ProjectCardComponent } from './project-card/project-card.component';
import { MatInputModule} from '@angular/material/input';
import { MatError } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import { CreateProjectComponent } from './create-project/create-project.component';
import { ProjectTaskComponent } from './project-task/project-task.component';
import { EmployeeRegisterComponent } from './employee-register/employee-register.component';
import { ManagerRegisterComponent } from './manager-register/manager-register.component';
import { TaskregisterComponent } from './taskregister/taskregister.component';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; // Required for date picker
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatRadioButton, MatRadioModule } from '@angular/material/radio';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { UserViewComponent } from './user-view/user-view.component';
import { TaskCardComponent } from './task-card/task-card.component';
import { ManagerLoginComponent } from './manager-login/manager-login.component';
import { EmployeeLoginComponent } from './employee-login/employee-login.component';


@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HomePageComponent,
    NavbarComponent,
    ManagerViewComponent,
    ProjectCardComponent,
    CreateProjectComponent,
    ProjectTaskComponent,
    EmployeeRegisterComponent,
    ManagerRegisterComponent,
    TaskregisterComponent,
    PageNotFoundComponent,
    UserViewComponent,
    TaskCardComponent,
    ManagerLoginComponent,
    EmployeeLoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserAnimationsModule,
    MatRadioButton,
    MatRadioModule,
    MatError
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
