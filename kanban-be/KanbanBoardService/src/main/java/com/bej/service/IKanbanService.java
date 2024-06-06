package com.bej.service;
import com.bej.domain.Employee;
import com.bej.domain.Task;
import java.util.List;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskNotFoundException;

public interface IKanbanService {
    void deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException;
    List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException;
}