package com.bej.controller;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.exception.*;
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

    //save employee mapping with endpoint /auth/save
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody Employee employee) throws UserAlreadyExistException {
        try{
            return new ResponseEntity<>(userAuthService.saveUser(employee), HttpStatus.CREATED);
        }
        catch(UserAlreadyExistException e){
            throw new UserAlreadyExistException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //login employee mapping with endpoint "/auth/login"
    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Employee employee) throws UserNotFoundException, InvalidCredentialsException {
        try{
            String loggedUser = userAuthService.login(employee.getUserId(), employee.getPassword());
            String token = tokenGenerator.createToken(employee);
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

    //save manager mapping with endpoint /auth/saveManager
    @PostMapping("/saveManager")
    public ResponseEntity<?> createManager(@RequestBody Manager manager) throws ManagerAlreadyExistException {
        try{
            return new ResponseEntity<>(userAuthService.saveManager(manager), HttpStatus.CREATED);
        }
        catch(ManagerAlreadyExistException e){
            throw new ManagerAlreadyExistException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //login manager mapping with endpoint "/auth/loginManager"
    @GetMapping("/loginManager")
    public ResponseEntity<?> loginManager(@RequestBody Manager manager) throws ManagerNotFoundException, InvalidCredentialsException {
        try{
            String loggedUser = userAuthService.loginManager(manager.getManagerId(),manager.getManagerPassword());
            String token = tokenGenerator.createTokenForManager(manager);
            Map<String,String> map = new HashMap<>();
            map.put("Message",loggedUser);
            map.put("Token",token);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        catch(ManagerNotFoundException e){
            throw new ManagerNotFoundException();
        }
        catch(InvalidCredentialsException e){
            throw new InvalidCredentialsException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
