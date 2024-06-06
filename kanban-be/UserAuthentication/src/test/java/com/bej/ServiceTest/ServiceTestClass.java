package com.bej.ServiceTest;
import com.bej.domain.User;
import com.bej.exception.InvalidCredentialsException;
import com.bej.exception.UserAlreadyExistException;
import com.bej.exception.UserNotFoundException;
import com.bej.repository.UserAuthRepository;
import com.bej.service.UserAuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTestClass {

    @Mock
    private UserAuthRepository userRepository;
    @InjectMocks
    private UserAuthService userService;
    @Test
    public void whenSaveUser_thenUserSaved() throws UserAlreadyExistException {
        User user = new User("testUser", "testPassword");
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals(user.getUserId(), savedUser.getUserId());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void whenLoginWithValidCredentials_thenLoggedIn() throws UserNotFoundException, InvalidCredentialsException {
        String userId = "testUser";
        String password = "testPassword";
        User user = new User(userId, password);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(userRepository.findByUserIdAndPassword(userId, password)).thenReturn(user);
        String result = userService.login(userId, password);
        assertEquals("User logged in successfully!!", result);
    }
}
