package com.bej.service;

import com.bej.domain.Employee;
import com.bej.exception.EmployeeNotFoundException;

public interface IKanbanService
{
    Employee registerEmployee(Employee employee);
    Employee updateEmployeeTaskInTaskList(String userId,Employee employee) throws EmployeeNotFoundException;

}
