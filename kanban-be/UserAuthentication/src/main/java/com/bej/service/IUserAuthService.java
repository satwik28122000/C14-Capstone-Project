package com.bej.service;

import com.bej.domain.Employee;
import com.bej.exception.InvalidCredentialsException;
import com.bej.exception.UserAlreadyExistException;
import com.bej.exception.UserNotFoundException;

public interface IUserAuthService {
    Employee saveUser(Employee employee) throws UserAlreadyExistException;
    String login(String userId,String password) throws UserNotFoundException, InvalidCredentialsException;
}
