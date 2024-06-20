import { Component } from '@angular/core';
import { Router, RouterModule, Routes } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
 constructor(private as: AuthService,private r: Router) { }

  logout() {
    this.as.logout();
  }
  loggedIn():boolean{
    return this.as.isLoggedIn();
  }
  
  
  navigateToHome() {
    const confirmHomeNavigation = confirm("Do you want to discard your changes and go to the home page?");
    if (confirmHomeNavigation) {
      this.r.navigate(['/']);
    }
  }
}
