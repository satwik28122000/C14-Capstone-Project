import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Employee }  from '../../models/employee';


@Component({
  selector: 'app-employee-register',
  templateUrl: './employee-register.component.html',
  styleUrl: './employee-register.component.css'
})
export class EmployeeRegisterComponent {
  registrationForm: FormGroup = new FormGroup({});
  constructor
  (private formBuilder : FormBuilder){}
 
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      userId: ['', Validators.required],
      userName: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)]],
      emailId: ['', [Validators.required]],
      designation: ['', [Validators.required], Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.passwordMatchValidator });
  }
  onSubmit() {
    console.log(this.registrationForm.valid);
    console.log(this.registrationForm.value);
    if (this.registrationForm.valid) {
      const employee: Employee ={
        userId: this.registrationForm.value.userId,
        userName: this.registrationForm.value.userName,
        emailId: this.registrationForm.value.userEmail,
        password: this.registrationForm.value.userPassword,
        designation: this.registrationForm.value.designation
        
      }
    }
    
  }
  passwordMatchValidator(formGroup: FormGroup){
    const password = formGroup.get('managerPassword')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password === confirmPassword ? null : {passwordMatch: true};
  }
  }

 
