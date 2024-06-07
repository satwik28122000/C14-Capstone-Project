package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskNotFoundException;

import java.util.List;

public interface IKanbanService {
    Employee registerEmployee(Employee employee);

    Employee updateEmployeeTaskInTaskList(String userId, Employee employee) throws EmployeeNotFoundException;


    List<Task> deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException;

    List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException;
}