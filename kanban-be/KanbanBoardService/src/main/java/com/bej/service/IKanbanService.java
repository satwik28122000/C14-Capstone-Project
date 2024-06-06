package com.bej.service;

import com.bej.domain.Employee;
<<<<<<< HEAD
import com.bej.exception.EmployeeNotFoundException;

public interface IKanbanService
{
    Employee registerEmployee(Employee employee);
    Employee updateEmployeeTaskInTaskList(String userId,Employee employee) throws EmployeeNotFoundException;

=======
import com.bej.domain.Task;

import java.util.List;

public interface IKanbanService
{
    List<Employee> getAllEmployee(String userId);
    Employee saveEmployeeTaskToTaskList(Task task, String userId);
>>>>>>> c14b98ada8b532c65b10ff85f92c34334b8833e7
}
