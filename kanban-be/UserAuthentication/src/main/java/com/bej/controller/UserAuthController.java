package com.bej.controller;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.exception.*;
import com.bej.security.ITokenGenerator;
import com.bej.service.IUserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Save user", description = "This will save new user")
    @ApiResponse(responseCode = "201", description = "User saved successfully")
    //save employee mapping with endpoint /auth/save
    @PostMapping("/users/save")
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
    @Operation(summary = "login user", description = "This will provide login  for employees")
    @ApiResponse(responseCode = "200", description = "employee login successfully")
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
    @Operation(summary = "save Manager", description = "This will save new manager")
    @ApiResponse(responseCode = "201", description = "Manager saved successfully")
    //save manager mapping with endpoint /auth/saveManager
    @PostMapping("/managers/saveManager")
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
    @Operation(summary = "Login manager", description = "This provide login for manager")
    @ApiResponse(responseCode = "200", description = "User saved successfully")
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
