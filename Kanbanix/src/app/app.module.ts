import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { NavbarComponent } from './navbar/navbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FooterComponent } from './footer/footer.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ManagerViewComponent } from './manager-view/manager-view.component';
import { ProjectCardComponent } from './project-card/project-card.component';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HomePageComponent,
    NavbarComponent,
    ManagerViewComponent,
    ProjectCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
