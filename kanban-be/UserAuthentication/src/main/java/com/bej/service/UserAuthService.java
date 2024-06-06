package com.bej.service;

import com.bej.domain.User;
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

    //register user
    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        if(repository.findById(user.getUserId()).isPresent()){
            throw new UserAlreadyExistException();
        }
        return repository.save(user);
    }

    //login user
    @Override
    public String login(String userId, String password) throws UserNotFoundException, InvalidCredentialsException {
        if(repository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = repository.findByUserIdAndPassword(userId,password);
        if(user == null){
            throw new InvalidCredentialsException();
        }
        return "User logged in successfully!!";
    }
}
