import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router: Router, private snackBar:MatSnackBar) { }

  isLoggedIn(): boolean {
    // Check if the user is logged in by verifying the token or session data
    return !!localStorage.getItem('token');
  }

  logout(): void {
    // Clear user session data
    localStorage.removeItem('token');
    localStorage.removeItem('Token');
    this.snackBar.open("Logging out!!","x")
    this.router.navigate(['/']);
  }
}
