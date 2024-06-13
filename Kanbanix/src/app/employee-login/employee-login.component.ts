import { Component } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Employee } from '../../models/employee';

@Component({
  selector: 'app-employee-login',
  templateUrl: './employee-login.component.html',
  styleUrls: ['./employee-login.component.css']
})
export class EmployeeLoginComponent {
  loginForm: FormGroup=new FormGroup ({});
    employee: any;
    constructor(private formBuilder: FormBuilder){}

    ngOnInit(): void {
      this.loginForm=this.formBuilder.group({
        userId: ['', [Validators.required]],
        password: ['', [Validators.required, Validators.minLength(6)]]
      });
    }

  onSubmit(form: NgForm) {
    console.log(this.loginForm.valid);
    console.log(this.loginForm.value);
    if (this.loginForm.valid) 
      {
        const employee: Employee={
          userId: this.loginForm.value.employeeId,
          password: this.loginForm.value.password
        }
      }
    }
      passwordMatchValidator(formGroup: FormGroup){
        const password = formGroup.get('password')?.value;
        // const confirmPassword = formGroup.get('confirmPassword')?.value;
        // return password === confirmPassword? null : {passwordMatch: true};
      }
    }
