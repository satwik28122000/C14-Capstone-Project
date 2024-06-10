package com.bej.repository;

import com.bej.domain.Project;
import com.bej.domain.Task;
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
public class ProjectRepositoryTest {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectRepositoryTest(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private Project project1;
    private Project project2;
    private Task task1;
    private Task task2;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
        task1 = new Task("301", "Task 1", "Description 1", "New", "Medium", "2024-06-09", null,"123");
        task2 = new Task("302", "Task 2", "Description 2", "In Progress", "High", "2024-06-10", null,"123");
        taskList.add(task1);
        taskList.add(task2);
        project1 = new Project("401", "Project X", "ProjectDescription", taskList);
        project2 = new Project("402", "Project Y", "ProjectDescription", taskList);
    }

    @AfterEach
    void tearDown() {
        task1 = null;
        task2 = null;
        project1 = null;
        project2 = null;
        projectRepository.deleteAll();
    }

    @Test
    public void testFindAllMethod() {
        projectRepository.save(project1);
        projectRepository.save(project2);
        assertEquals(2, projectRepository.findAll().size());

    }

    @Test
    public void testDeleteByIdMethod() {
        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.deleteById("402");
        assertEquals(1, projectRepository.findAll().size());
        assertFalse(projectRepository.findById("402").isPresent());
    }

    @Test
    public void testFindByIdMethod() {
        projectRepository.save(project1);
        assertTrue(projectRepository.findById("401").isPresent());
    }

    @Test
    public void testSaveMethod() {
        Project savedProject = projectRepository.save(project1);
        assertNotNull(savedProject);
        assertEquals("Project X", savedProject.getProjectName());
    }
}
