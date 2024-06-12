// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-employee-login',
//   templateUrl: './employee-login.component.html',
//   styleUrl: './employee-login.component.css'
// })
// export class EmployeeLoginComponent {

// }

import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-employee-login',
  templateUrl: './employee-login.component.html',
  styleUrls: ['./employee-login.component.css']
})
export class EmployeeLoginComponent {
  employee = {
    employeeId: '',
    password: ''
  };

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Employee ID:', this.employee.employeeId);
      console.log('Password:', this.employee.password);
      // Add your login logic here
    }
  }
}



// registrationForm: FormGroup = new FormGroup({});
// constructor
// (private formBuilder : FormBuilder){}

// ngOnInit(): void {
//   this.registrationForm = this.formBuilder.group({
//     userId: ['', Validators.required],
//password: ['', [Validators.required, Validators.minLength(6)]]


// onSubmit() {
//   console.log(this.registrationForm.valid);
//   console.log(this.registrationForm.value);
//   if (this.registrationForm.valid) {
//     const employee: Employee ={
//       userId: this.registrationForm.value.userId,
//       password: this.registrationForm.value.userPassword,

// passwordMatchValidator(formGroup: FormGroup){
//   const password = formGroup.get('managerPassword')?.value;
//   const confirmPassword = formGroup.get('confirmPassword')?.value;
//   return password === confirmPassword ? null : {passwordMatch: true};
// }