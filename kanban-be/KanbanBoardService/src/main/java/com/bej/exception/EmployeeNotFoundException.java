package com.bej.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

<<<<<<< HEAD
@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Employee having this ID doesn't exist!!")
public class EmployeeNotFoundException extends Throwable {
=======

public class EmployeeNotFoundException extends Exception
{

>>>>>>> c14b98ada8b532c65b10ff85f92c34334b8833e7
}
