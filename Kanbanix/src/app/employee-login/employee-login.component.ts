import { Component } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Employee } from '../../Models/Employee';
import { RouterService } from '../services/router.service';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-login',
  templateUrl: './employee-login.component.html',
  styleUrls: ['./employee-login.component.css']
})
export class EmployeeLoginComponent {
    employee: Employee={ userId: '', password: '' };
    constructor(private routerService:RouterService,private employeeService:EmployeeService){}

    ngOnInit(): void { }


      onSubmit(form: NgForm) {
        this.employeeService.loginEmployee(form.value).subscribe({
         next: (res:any) => {
            console.log(res);
            localStorage.setItem("token",res.Token);
             this.routerService.redirectToUserView(form.value?.userId);
          },
          error: err =>{
            console.log(form.value);
            console.log(err);
          }
        })
  
    
      }
    









      
}
