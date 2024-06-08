package com.bej.RepositoryTest;
import com.bej.domain.Employee;
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
    private Employee employee;
    @BeforeEach
    public void setUp() throws Exception {
        employee = new Employee("psk", "111");
        userRepository.save(employee);
    }

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        employee = null;
    }

    @Test
    public void testSaveUserSuccess() {
        Employee object = userRepository.findById(employee.getUserId()).get();
        assertEquals(employee.getUserId(), object.getUserId());
    }

    @Test
    public void testLoginUserSuccess() {
        Employee object = userRepository.findById(employee.getUserId()).get();
        assertEquals(employee.getUserId(), object.getUserId());
    }
}