package com.bej.service;
import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeAlreadyExistsException;

import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskAlreadyExistsException;

import com.bej.exception.EmployeeAlreadyExistsException;

import com.bej.exception.EmployeeNotFoundException;

import com.bej.exception.TaskNotFoundException;

import java.util.List;



public interface IKanbanService {


    List<Employee> getAllEmployee(String userId) throws EmployeeNotFoundException;
    Employee registerEmployee(Employee employee) throws EmployeeAlreadyExistsException;
    Employee updateEmployeeTaskInTaskList(String userId, Task employee) throws EmployeeNotFoundException,TaskNotFoundException;

    Employee saveEmployeeTaskToTaskList(Task task, String userId) throws EmployeeNotFoundException, TaskAlreadyExistsException;
    List<Task> deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException;
    List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException;
}

