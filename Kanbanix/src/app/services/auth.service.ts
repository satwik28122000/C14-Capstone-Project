import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }
 

  private loggedIn: boolean = false;

  isLoggedIn(): boolean {
    // Replace with actual login logic
    return this.loggedIn;
  }

  login() {
    this.loggedIn = true;
  }

  logout() {
    this.loggedIn = false;
  }
}


