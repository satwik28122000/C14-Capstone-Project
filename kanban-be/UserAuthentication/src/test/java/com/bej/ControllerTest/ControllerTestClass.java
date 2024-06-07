package com.bej.ControllerTest;
import com.bej.controller.UserAuthController;
import com.bej.domain.User;
import com.bej.security.ITokenGenerator;
import com.bej.service.IUserAuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTestClass {

    @Mock
    private IUserAuthService userService;

    @Mock
    private ITokenGenerator tokenGenerator;

    @InjectMocks
    private UserAuthController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSaveUser_thenCreated() throws Exception {
        User user = new User("testUser", "testPassword");
        when(userService.saveUser(user)).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/saveUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
        // You may need to update this to match the actual JSON response structure
        assertEquals("{\"userId\":\"testUser\",\"password\":\"testPassword\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void whenLoginWithValidCredentials_thenTokenReturned() throws Exception {
        String userId = "testUser";
        String password = "testPassword";
        User user = new User(userId, password);
        String token = "testToken";
        when(userService.login(userId, password)).thenReturn("User logged in successfully!!");
        when(tokenGenerator.createToken(user)).thenReturn(token);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals("testToken", result.getResponse().getContentAsString());
    }
}
