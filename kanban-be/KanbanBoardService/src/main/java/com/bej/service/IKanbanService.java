package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Task;

import java.util.List;

public interface IKanbanService
{
    Employee registerEmployee(Employee employee);
    void deleteTaskFromEmployee(String userId, String taskId);
    List<Task> getAllEmployeeTaskFromTaskList(String userId);
}
