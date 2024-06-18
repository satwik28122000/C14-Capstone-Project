import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router: Router) { }

  isLoggedIn(): boolean {
    // Check if the user is logged in by verifying the token or session data
    return !!localStorage.getItem('token');
  }

  logout(): void {
    // Clear user session data
    localStorage.removeItem('token');
    // Optionally, clear other session data
     localStorage.removeItem('user');
    // Navigate to the login page or home page
     //window.alert("Do you Sure! want to logout?")
    this.router.navigate(['/']);
  }
}
