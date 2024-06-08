package com.bej.ServiceTest;
import com.bej.domain.Employee;
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
        Employee employee = new Employee("testUser", "testPassword");
        when(userRepository.findById(employee.getUserId())).thenReturn(java.util.Optional.empty());
        when(userRepository.save(employee)).thenReturn(employee);
        Employee savedEmployee = userService.saveUser(employee);
        assertNotNull(savedEmployee);
        assertEquals(employee.getUserId(), savedEmployee.getUserId());
        assertEquals(employee.getPassword(), savedEmployee.getPassword());
    }

    @Test
    public void whenLoginWithValidCredentials_thenLoggedIn() throws UserNotFoundException, InvalidCredentialsException {
        String userId = "testUser";
        String password = "testPassword";
        Employee employee = new Employee(userId, password);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(employee));
        when(userRepository.findByUserIdAndPassword(userId, password)).thenReturn(employee);
        String result = userService.login(userId, password);
        assertEquals("Employee logged in successfully!!", result);
    }
}
