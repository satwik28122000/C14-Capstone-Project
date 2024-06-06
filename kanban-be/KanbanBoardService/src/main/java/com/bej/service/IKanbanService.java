package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Task;

import java.util.List;

public interface IKanbanService
{
    List<Employee> getAllEmployee(String userId);
    Employee saveEmployeeTaskToTaskList(Task task, String userId);
}
