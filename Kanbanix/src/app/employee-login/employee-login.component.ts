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

<<<<<<< HEAD
  onSubmit(form: NgForm) {
      this.employeeService.loginEmployee(form.value).subscribe({
=======

      onSubmit(form: NgForm) {
        this.employeeService.loginEmployee(form.value).subscribe({
        
>>>>>>> 56bbb2eb17fe7afccfeb552fc11c760b97de3011
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
