package com.bej.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Employee having this ID doesn't exist!!")
public class EmployeeNotFoundException extends Throwable {
}
