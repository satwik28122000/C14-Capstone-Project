package com.bej.service;

import com.bej.domain.Employee;
import com.bej.exception.InvalidCredentialsException;
import com.bej.exception.UserAlreadyExistException;
import com.bej.exception.UserNotFoundException;
import com.bej.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements IUserAuthService{
    //UserAuthRepository declaration
    private UserAuthRepository repository;
    @Autowired
    public UserAuthService(UserAuthRepository repository) {
        this.repository = repository;
    }

    //register employee
    @Override
    public Employee saveUser(Employee employee) throws UserAlreadyExistException {
        if(repository.findById(employee.getUserId()).isPresent()){
            throw new UserAlreadyExistException();
        }
        return repository.save(employee);
    }

    //login user
    @Override
    public String login(String userId, String password) throws UserNotFoundException, InvalidCredentialsException {
        if(repository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        Employee employee = repository.findByUserIdAndPassword(userId,password);
        if(employee == null){
            throw new InvalidCredentialsException();
        }
        return "Employee logged in successfully!!";
    }
}
