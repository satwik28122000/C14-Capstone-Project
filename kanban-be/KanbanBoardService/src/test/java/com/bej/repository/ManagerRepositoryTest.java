package com.bej.repository;

import com.bej.domain.Manager;
import com.bej.domain.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ManagerRepositoryTest {
    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerRepositoryTest(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    private Manager manager1;
    private Manager manager2;
    private Project project1;
    private Project project2;
    private List<Project> projectList;
    @BeforeEach
    void setUp() {
        projectList = new ArrayList<>();
        project1 = new Project("201", "Project A", "Description A",null);
        project2 = new Project("202", "Project B", "Description B",null);
        projectList.add(project1);
        projectList.add(project2);
        manager1 = new Manager("manager1", "Manager One", "manager1@example.com", "password1", projectList);
        manager2 = new Manager("manager2", "Manager Two", "manager2@example.com", "password2", projectList);
    }

    @AfterEach
    void tearDown() {
        project1 = null;
        project2 = null;
        manager1 = null;
        manager2 = null;
        managerRepository.deleteAll();
    }

    @Test
    public void testFindAllMethod() {
        managerRepository.save(manager1);
        managerRepository.save(manager2);
        assertEquals(2, managerRepository.findAll().size());
    }

    @Test
    public void testDeleteByIdMethod() {
        managerRepository.save(manager1);
        managerRepository.save(manager2);
        managerRepository.deleteById("manager2");
        assertEquals(1, managerRepository.findAll().size());
        assertFalse(managerRepository.findById("manager2").isPresent());
    }

    @Test
    public void testFindByIdMethod() {
        managerRepository.save(manager1);
        assertTrue(managerRepository.findById("manager1").isPresent());
    }

    @Test
    public void testSaveMethod() {
        Manager savedManager = managerRepository.save(manager1);
        assertNotNull(savedManager);
        assertEquals("Manager One", savedManager.getManagerName());
    }
}
