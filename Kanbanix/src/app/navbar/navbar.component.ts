import { Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
 constructor(private as: AuthService) { }

  logout() {
    this.as.logout();
  }

}
