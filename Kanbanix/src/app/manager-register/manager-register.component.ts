import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Manager }  from '../../models/manager';


@Component({
  selector: 'app-manager-register',
  templateUrl: './manager-register.component.html',
  styleUrl: './manager-register.component.css'
})
export class ManagerRegisterComponent {
  registrationForm: FormGroup = new FormGroup({});
  constructor
  (private formBuilder : FormBuilder){}
 
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      managerId: ['', [Validators.required]],
      managerName: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)]],
      managerEmail: ['', [Validators.required]],
      managerPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.passwordMatchValidator });
  }
  onSubmit() {
    console.log(this.registrationForm.valid);
    console.log(this.registrationForm.value);
    if (this.registrationForm.valid) {
      const manager: Manager ={
        managerId: this.registrationForm.value.managerId,
        managerName: this.registrationForm.value.managerName,
        managerEmail: this.registrationForm.value.managerEmail,
        managerPassword: this.registrationForm.value.managerPassword
      }
    }
    
  }
  passwordMatchValidator(formGroup: FormGroup){
    const password = formGroup.get('managerPassword')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password === confirmPassword ? null : {passwordMatch: true};
  }
  }

 