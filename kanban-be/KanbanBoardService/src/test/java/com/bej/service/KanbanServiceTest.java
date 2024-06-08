package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class KanbanServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private KanbanServiceImpl kanbanService;
    private Employee employee1;
    private Employee employee2;
    private Task task1;
    private Task task2;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        task1=new Task("101","Generate token","Generate token in user auth service","Assigned","High","09-06-2024",employee1);
        task2=new Task("102","Kanban Service","Implement register employee method","Assigned","High","10-06-2024",employee2);
        employee1 = new Employee("pallavi@12","Pallavi","qwerty123", "Full Stack Developer","pallavi@gmail.com",taskList);
        employee2 = new Employee("priya@12","Priyanka","qwerty123", "Full Stack Developer","priya@gmail.com",taskList);
    }

    @AfterEach
    void tearDown() {
        task1=null;
        task2=null;
        employee1=null;
        employee2=null;
        employeeRepository.deleteAll();
    }


}
