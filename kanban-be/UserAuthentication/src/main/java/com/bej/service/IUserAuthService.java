package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.exception.*;

public interface IUserAuthService {
    Employee saveUser(Employee employee) throws UserAlreadyExistException;
    String login(String userId,String password) throws UserNotFoundException, InvalidCredentialsException;
    Manager saveManager(Manager manager) throws ManagerAlreadyExistException;
    String loginManager(String managerId,String password) throws ManagerNotFoundException, InvalidCredentialsException;
}
