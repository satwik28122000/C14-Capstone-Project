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
    localStorage.removeItem('Token');
    alert("Logging out..");
    this.router.navigate(['/']);
  }
}
