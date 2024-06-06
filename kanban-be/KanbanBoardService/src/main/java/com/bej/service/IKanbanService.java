package com.bej.service;

//import com.bej.domain.Employee;
//
//import com.bej.domain.Task;
//import com.bej.exception.EmployeeNotFoundException;
//
//import java.util.List;
//
//public interface IKanbanService
//{
//    Employee registerEmployee(Employee employee);
//    Employee updateEmployeeTaskInTaskList(String userId,Employee employee) throws EmployeeNotFoundException;


import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskAlreadyExistsException;
import com.bej.exception.TaskNotFoundException;

import java.util.List;

public interface IKanbanService
{

    List<Employee> getAllEmployee(String userId) throws Exception;
    Employee saveEmployeeTaskToTaskList(Task task, String userId) throws EmployeeNotFoundException , TaskAlreadyExistsException;


}
