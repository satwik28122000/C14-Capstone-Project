package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.exception.*;
import com.bej.repository.ManagerRepository;
import com.bej.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements IUserAuthService{
    //UserAuthRepository declaration
    private UserAuthRepository repository;
    private ManagerRepository managerRepository;
    @Autowired
    public UserAuthService(UserAuthRepository repository,ManagerRepository managerRepository) {
        this.repository = repository;
        this.managerRepository=managerRepository;
    }

    //register employee
    @Override
    public Employee saveUser(Employee employee) throws UserAlreadyExistException {
        if(repository.findById(employee.getUserId()).isPresent()){
            throw new UserAlreadyExistException();
        }
        return repository.save(employee);
    }

    //login employee
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

    @Override
    public Manager saveManager(Manager manager) throws ManagerAlreadyExistException {
        if(managerRepository.findById(manager.getManagerId()).isPresent()){
            throw new ManagerAlreadyExistException();
        }
        return managerRepository.save(manager);
    }

    @Override
    public String loginManager(String managerId, String password) throws ManagerNotFoundException, InvalidCredentialsException {
        if(managerRepository.findById(managerId).isEmpty()){
            throw new ManagerNotFoundException();
        }
        Manager manager = managerRepository.findByManagerIdAndManagerPassword(managerId,password);
        if(manager == null){
            throw new InvalidCredentialsException();
        }
        return "Manager logged in successfully!!";
    }
}
