

import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Manager } from '../../models/manager';
import { ManagerService } from '../services/manager.service';
import { RouterService } from '../services/router.service';
import { Manager } from '../../models/manager';

@Component({
  selector: 'app-manager-register',
  templateUrl: './manager-register.component.html',
  styleUrls: ['./manager-register.component.css']
})
export class ManagerRegisterComponent implements OnInit {
  registrationForm: FormGroup = new FormGroup({});
  constructor
  (private formBuilder : FormBuilder, private managerService:ManagerService,private routerService:RouterService){}
 

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      managerId: ['', [Validators.required]],
      managerName: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)]],
      managerEmail: ['', [Validators.required, Validators.email]],
      managerPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  onSubmit() {
      this.managerService.registerManager(this.registrationForm.value).subscribe({
        next: res => {
          console.log(res);
          this.routerService.redirectToManagerLogin();
        },
        error: err => {
          console.log(err);
        }
      })
  }
  // passwordMatchValidator(formGroup: FormGroup){
  //   const password = formGroup.get('managerPassword')?.value;
  //   const confirmPassword = formGroup.get('confirmPassword')?.value;
  //   return password === confirmPassword ? null : {passwordMatch: true};
  // }
  
    // if (this.registrationForm.valid) {
    //   const manager: Manager = {
    //     managerId: this.registrationForm.value.managerId,
    //     managerName: this.registrationForm.value.managerName,
    //     managerEmail: this.registrationForm.value.managerEmail,
    //     managerPassword: this.registrationForm.value.managerPassword
    //   };
    //   // Process the manager object as needed
    // }
  

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


 