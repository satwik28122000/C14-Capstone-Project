import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Manager } from '../../models/manager';
import { ManagerService } from '../services/manager.service';
import { RouterService } from '../services/router.service';
import { Manager } from '../../Models/Manager';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manager-register',
  templateUrl: './manager-register.component.html',
  styleUrls: ['./manager-register.component.css']
})
export class ManagerRegisterComponent implements OnInit {
  registrationForm: FormGroup = new FormGroup({});
  constructor
  (private formBuilder : FormBuilder,
     private managerService:ManagerService,
     private routerService:RouterService,
    private snackBar:MatSnackBar){}
 

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      managerId: ['', [Validators.required]],
      managerName: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)]],
      managerEmail: ['', [Validators.required, Validators.pattern("^[a-zA-Z][a-zA-Z0-9.]*@[a-zA-Z-]+(\.[a-zA-Z]+)+$")]],
      managerPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  onSubmit() {
      this.managerService.registerManager(this.registrationForm.value).subscribe({
        next: res => {
          console.log(res);
          this.snackBar.open("Manager registered successfully!!")
          this.routerService.redirectToManagerLogin();
        },
        error: err => {
          console.log(err);
        }
      })
  }
  
  

  passwordMatchValidator(ac: AbstractControl) {
    let password = ac.get('managerPassword')?.value;
    let confirmPassword = ac.get('confirmPassword')?.value;
    console.log(password + " " + confirmPassword);
    if (password !== confirmPassword) {
      return { pError: true };
    } else {
      return null;
    }
  }

  

  get managerId() : AbstractControl {
    return this.registrationForm.get('managerId')!
  }

  get managerName(): AbstractControl {
    return this.registrationForm.get('managerName')!;
  }

  get managerEmail(): AbstractControl {
    return this.registrationForm.get('managerEmail')!;
  }

  get managerPassword(): AbstractControl {
    return this.registrationForm.get('managerPassword')!;
  }

  get confirmPassword(): AbstractControl {
    return this.registrationForm.get('confirmPassword')!;
  }
}


 