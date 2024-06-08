package com.bej.controller;

import com.bej.domain.User;
import com.bej.exception.InvalidCredentialsException;
import com.bej.exception.UserAlreadyExistException;
import com.bej.exception.UserNotFoundException;
import com.bej.security.ITokenGenerator;
import com.bej.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private IUserAuthService userAuthService;
    private ITokenGenerator tokenGenerator;
    @Autowired
    public UserAuthController(IUserAuthService userAuthService,ITokenGenerator tokenGenerator) {
        this.userAuthService = userAuthService;
        this.tokenGenerator = tokenGenerator;
    }

    //save user mapping with endpoint /auth/save
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistException {
        try{
            return new ResponseEntity<>(userAuthService.saveUser(user), HttpStatus.CREATED);
        }
        catch(UserAlreadyExistException e){
            throw new UserAlreadyExistException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //login user mapping with endpoint "/auth/login"
    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException, InvalidCredentialsException {
        try{
            String loggedUser = userAuthService.login(user.getUserId(),user.getPassword());
            String token = tokenGenerator.createToken(user);
            Map<String,String> map = new HashMap<>();
            map.put("Message",loggedUser);
            map.put("Token",token);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        catch(UserNotFoundException e){
            throw new UserNotFoundException();
        }
        catch(InvalidCredentialsException e){
            throw new InvalidCredentialsException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
