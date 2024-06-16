import { Component } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Manager } from '../../Models/Manager';
import { RouterService } from '../services/router.service';
import { ManagerService } from '../services/manager.service';

@Component({
  selector: 'app-manager-login',
  templateUrl: './manager-login.component.html',
  styleUrls: ['./manager-login.component.css']
})
export class ManagerLoginComponent {
  loginForm: FormGroup=new FormGroup ({});
  manager: Manager ={};
  constructor
  (private formBuilder : FormBuilder,private routerService:RouterService,private managerService:ManagerService){}

  ngOnInit(): void {
      this.loginForm = this.formBuilder.group({
        managerId: ['', [Validators.required]],
        managerPassword: ['', [Validators.required, Validators.minLength(6)]]
      });
  }

  onSubmit(form: NgForm) {
  
     
    this.managerService.loginManager(form.value).subscribe({
        
        next: (res:any) => {
          console.log(res);
          localStorage.setItem("token",res.Token);
          
          this.routerService.redirectToManagerView(form.value?.managerId);
        },
        error: err =>{
          console.log(form.value);
          console.log(err);
        }
      })

  }
             passwordMatchValidator(formGroup: FormGroup){
          const password = formGroup.get('managerPassword')?.value;
          const confirmPassword = formGroup.get('confirmPassword')?.value;
          return password === confirmPassword ? null : {passwordMatch: true};
      
    }
  }
