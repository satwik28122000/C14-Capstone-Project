// import { Component } from '@angular/core';
// import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Manager } from '../../models/manager';



// @Component({
//   selector: 'app-manager-register',
//   templateUrl: './manager-register.component.html',
//   styleUrl: './manager-register.component.css'
// })
// export class ManagerRegisterComponent {
//   registrationForm: FormGroup = new FormGroup({});
//   constructor
//   (private formBuilder : FormBuilder){}
 
//   ngOnInit(): void {
//     this.registrationForm = this.formBuilder.group({
//       managerId: ['', [Validators.required]],
//       managerName: ['', [Validators.required, Validators.minLength(10), Validators.pattern("/^[A-Z a-z]+$/")]],
//       managerEmail: ['', [Validators.required]],
//       managerPassword: ['', [Validators.required, Validators.minLength(6)]],
//       confirmPassword: ['', [Validators.required]]
//     }, { validators: this.passwordMatchValidator });
//   }
//   onSubmit() {
//     console.log(this.registrationForm.valid);
//     console.log(this.registrationForm.value);
//     if (this.registrationForm.valid) {
//       const manager: Manager ={
//         managerId: this.registrationForm.value.managerId,
//         managerName: this.registrationForm.value.managerName,
//         managerEmail: this.registrationForm.value.managerEmail,
//         managerPassword: this.registrationForm.value.managerPassword
//       }
//     }
    
//   }

//   passwordMatchValidator(ac:AbstractControl){
//     let password=ac.get('managerPassword')?.value;
//     let confirmPassword=ac.get('confirmPassword')?.value;
//     console.log(password+" "+confirmPassword);
//     if(password!==confirmPassword)
//       {
//         return {pError:true};
//       }else{
//         return null;
//       }

    
//   }

  
//   }

import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Manager } from '../../models/manager';

@Component({
  selector: 'app-manager-register',
  templateUrl: './manager-register.component.html',
  styleUrls: ['./manager-register.component.css']
})
export class ManagerRegisterComponent implements OnInit {
  registrationForm: FormGroup = new FormGroup({});
  
  constructor(private formBuilder: FormBuilder) {}

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
    console.log(this.registrationForm.valid);
    console.log(this.registrationForm.value);
    if (this.registrationForm.valid) {
      const manager: Manager = {
        managerId: this.registrationForm.value.managerId,
        managerName: this.registrationForm.value.managerName,
        managerEmail: this.registrationForm.value.managerEmail,
        managerPassword: this.registrationForm.value.managerPassword
      };
      // Process the manager object as needed
    }
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

  get managerId(): AbstractControl {
    return this.registrationForm.get('managerId')!;
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


 