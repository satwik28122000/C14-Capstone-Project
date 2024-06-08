package com.bej.ControllerTest;
import com.bej.controller.UserAuthController;
import com.bej.domain.Employee;
import com.bej.exception.UserAlreadyExistException;
import com.bej.service.IUserAuthService;
import com.bej.security.ITokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ControllerTestClass {

    private MockMvc mockMvc;

    @Mock
    private IUserAuthService userAuthService;

    @Mock
    private ITokenGenerator tokenGenerator;

    @InjectMocks
    private UserAuthController userAuthController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAuthController).build();
    }

    @Test
    public void givenUserToSaveReturnUserSuccess() throws Exception, UserAlreadyExistException {
        Employee employee = new Employee("p@gmail.com", "password");

        when(userAuthService.saveUser(any(Employee.class))).thenReturn(employee);
        mockMvc.perform(post("/auth/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().isCreated());
    }
//    @Test
//    public void givenInvalidLoginCredentialsReturnUnauthorized() throws Exception, UserAlreadyExistException {
//        Employee user = new Employee("p@gmail.com", "wrong_password");
//
//        when(userAuthService.login(anyString(), anyString())).thenThrow(new InvalidCredentialsException());
//
//        mockMvc.perform(get("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(user)))
//                .andExpect(status().isUnauthorized());
//    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
