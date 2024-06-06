package com.bej.service;

import com.bej.domain.User;
import com.bej.exception.InvalidCredentialsException;
import com.bej.exception.UserAlreadyExistException;
import com.bej.exception.UserNotFoundException;

public interface IUserAuthService {
    User saveUser(User user) throws UserAlreadyExistException;
    String login(String userId,String password) throws UserNotFoundException, InvalidCredentialsException;
}
