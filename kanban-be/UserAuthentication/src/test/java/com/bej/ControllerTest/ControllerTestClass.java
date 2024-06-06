package com.bej.ControllerTest;
import com.bej.controller.UserAuthController;
import com.bej.domain.User;
import com.bej.exception.InvalidCredentialsException;
import com.bej.exception.UserAlreadyExistException;
import com.bej.exception.UserNotFoundException;
import com.bej.security.ITokenGenerator;
import com.bej.service.IUserAuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@SpringBootTest
public class ControllerTestClass {

    @Mock
    private IUserAuthService userService;

    @Mock
    private ITokenGenerator tokenGenerator;

    @InjectMocks
    private UserAuthController userController;

    @Test
    public void whenSaveUser_thenCreated() throws UserAlreadyExistException {
        User user = new User("testUser", "testPassword");
        when(userService.saveUser(user)).thenReturn(user);
        ResponseEntity<?> response = userController.saveUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user, response.getBody());
    }

    @Test
    public void whenLoginWithValidCredentials_thenTokenReturned() throws UserNotFoundException, InvalidCredentialsException {
        String userId = "testUser";
        String password = "testPassword";
        User user = new User(userId, password);
        String token = "testToken";
        when(userService.login(userId, password)).thenReturn("User logged in successfully!!");
        when(tokenGenerator.createToken(user)).thenReturn(token);
        ResponseEntity<?> response = userController.loginUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody());
    }
}
