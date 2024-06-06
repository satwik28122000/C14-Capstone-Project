package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanbanServiceImpl implements IKanbanService
{

    @Override
    public List<Employee> getAllEmployee(String userId) throws Exception
    {

        return null;
    }

    @Override
    public Employee saveEmployeeTaskToTaskList(Task task, String userId) throws EmployeeNotFoundException, TaskAlreadyExistsException {
        return null;
    }
}
