import { Component } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Employee } from '../../Models/Employee';
import { RouterService } from '../services/router.service';
import { EmployeeService } from '../services/employee.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-employee-login',
  templateUrl: './employee-login.component.html',
  styleUrls: ['./employee-login.component.css']
})
export class EmployeeLoginComponent
{
    employee: Employee={ userId: '', password: '' };
    constructor(private routerService:RouterService,
      private employeeService:EmployeeService,
    private snackBar:MatSnackBar){}

    ngOnInit(): void { }


      onSubmit(form: NgForm) {
        this.employeeService.loginEmployee(form.value).subscribe({


          next: (res:any) => {
            console.log(res);
            localStorage.setItem("token",res.Token);
            this.snackBar.open("Logged in successfully!")
             this.routerService.redirectToUserView(form.value?.userId);
          },
          error: err =>{
            console.log(form.value);
            alert("Invalid credentials or New User? Register Now!!");
          }
        })


      }
    }

