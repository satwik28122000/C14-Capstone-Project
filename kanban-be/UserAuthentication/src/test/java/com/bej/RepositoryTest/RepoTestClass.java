package com.bej.RepositoryTest;
import com.bej.domain.User;
import com.bej.repository.UserAuthRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoTestClass {
    @Autowired
    private UserAuthRepository userRepository;
    private User user;
    @BeforeEach
    public void setUp() throws Exception {
        user = new User("psk", "111");
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        user = null;
    }

    @Test
    public void testSaveUserSuccess() {
        User object = userRepository.findById(user.getUserId()).get();
        assertEquals(user.getUserId(), object.getUserId());
    }

    @Test
    public void testLoginUserSuccess() {
        User object = userRepository.findById(user.getUserId()).get();
        assertEquals(user.getUserId(), object.getUserId());
    }
}