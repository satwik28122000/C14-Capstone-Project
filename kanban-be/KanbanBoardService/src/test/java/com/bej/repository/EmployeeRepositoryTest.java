package com.bej.repository;

import com.bej.domain.Employee;
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
public class EmployeeRepositoryTest {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    private Employee employee1;
    private Employee employee2;
    private Task task1;
    private Task task2;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList=new ArrayList<>();
        task1=new Task("101","Generate token","Generate token in user auth service","Assigned","High","09-06-2024",employee1,"12321");
        task2=new Task("102","Kanban Service","Implement register employee method","Assigned","High","10-06-2024",employee2,"12321");
        taskList.add(task1);
        taskList.add(task2);
        employee1 = new Employee("pallavi@12","Pallavi","qwerty123", "Full Stack Developer","pallavi@gmail.com","Sangeetha@123",taskList);
        employee2 = new Employee("priya@12","Priyanka","qwerty123", "Full Stack Developer","priya@gmail.com","Sangeetha@123",taskList);
    }

    @AfterEach
    void tearDown() {
        task1=null;
        task2=null;
        employee1=null;
        employee2=null;
        employeeRepository.deleteAll();
    }

    @Test
    public void testFindAllMethod(){
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        assertEquals(2,employeeRepository.findAll().size());
    }

//    @Test
//    public void testDeleteByIdMethod(){
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
//        employeeRepository.deleteById("priya@12");
//        assertEquals(1,employeeRepository.findAll().size());
//        assertFalse(employeeRepository.findById("priya@12").isPresent());
//    }
    @Test
    public void testFindByIdMethod() {
        employeeRepository.save(employee1);
        assertTrue(employeeRepository.findById("pallavi@12").isPresent());
    }

    @Test
    public void testSaveMethod() {
        Employee savedEmployee = employeeRepository.save(employee1);
        assertNotNull(savedEmployee);
        assertEquals("Pallavi", savedEmployee.getUserName());
    }
}
