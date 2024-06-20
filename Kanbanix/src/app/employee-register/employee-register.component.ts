import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeService } from '../services/employee.service';
import { RouterService } from '../services/router.service';
import { CanComponentDeactivate } from '../guard/deactive-auth.guard';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-employee-register',
  templateUrl: './employee-register.component.html',
  styleUrls: ['./employee-register.component.css']
})
export class EmployeeRegisterComponent implements OnInit, CanComponentDeactivate {
  registrationForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private employeeService: EmployeeService,
    private routerService: RouterService,
    private snackBar:MatSnackBar
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      userId: ['', [Validators.required]],
      userName: ['', [Validators.required, Validators.minLength(4), Validators.pattern(/^[A-Z a-z]+$/)]],
      emailId: ['', [Validators.required, Validators.pattern("^[a-zA-Z][a-zA-Z0-9.]*@[a-zA-Z-]+(\.[a-zA-Z]+)+$")]],
      designation: ['', [Validators.required, Validators.minLength(2), Validators.pattern(/^[A-Z a-z]+$/)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.passwordMatchValidator });
  }

  onSubmit() {
    this.employeeService.registerEmployee(this.registrationForm.value).subscribe({
      next: res => {
        console.log(res);
        this.snackBar.open("User registered successfully","x")
        // Mark the form as pristine after successful submission
        this.registrationForm.markAsPristine();
        this.routerService.redirectToUserLogin();
      },
      error: err => {
        console.log(err);
      }
    });
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

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (this.registrationForm.dirty) {
      return confirm("You have unsaved changes. Do you really want to leave?");
    }
    return true;
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
