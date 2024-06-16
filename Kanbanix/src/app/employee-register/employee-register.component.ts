import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Employee }  from '../../Models/Employee';
import { EmployeeService } from '../services/employee.service';
import { RouterService } from '../services/router.service';


@Component({
  selector: 'app-employee-register',
  templateUrl: './employee-register.component.html',
  styleUrl: './employee-register.component.css'
})
export class EmployeeRegisterComponent implements OnInit {
  registrationForm: FormGroup = new FormGroup({});
  constructor
  (private formBuilder : FormBuilder, private employeeService: EmployeeService, private routerService: RouterService){}
 
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      userId: ['', [Validators.required]],
      userName: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)]],
      emailId: ['', [Validators.required]],
      designation: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[A-Z a-z]+$/)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.passwordMatchValidator });
  }
  onSubmit() {
    this.employeeService.registerEmployee(this.registrationForm.value).subscribe({
      next : res =>{
        console.log(res);
        this.routerService.redirectToUserLogin();
      },
      error : err => {
        console.log(err);
      }
    })
        
      }
    
      passwordMatchValidator(ac: AbstractControl) {
        let password = ac.get('password')?.value;
        let confirmPassword = ac.get('confirmPassword')?.value;
        console.log(password + " " + confirmPassword);
        if (password !== confirmPassword) {
          return { pError: true };
        } else {
          return null;
        }
      }
    
      get userId(): AbstractControl {
        return this.registrationForm.get('userId')!;
      }
    
      get userName(): AbstractControl {
        return this.registrationForm.get('userName')!;
      }
    
      get emailId(): AbstractControl {
        return this.registrationForm.get('emailId')!;
      }

      get designation(): AbstractControl {
        return this.registrationForm.get('designation')!;
      }
    
      get password(): AbstractControl {
        return this.registrationForm.get('password')!;
      }
    
      get confirmPassword(): AbstractControl {
        return this.registrationForm.get('confirmPassword')!;
      }
  }

 
