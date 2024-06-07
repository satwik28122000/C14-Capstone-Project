package com.bej.service;

import com.bej.domain.Employee;


import com.bej.domain.Task;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskAlreadyExistsException;


import com.bej.domain.Task;
import com.bej.exception.EmployeeAlreadyExistsException;

import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskNotFoundException;
import com.bej.repository.EmployeeRepository;
import com.bej.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.util.List;


@Service

public class KanbanServiceImpl implements IKanbanService
{

    private EmployeeRepository employeeRepository;
    private ManagerRepository managerRepository;
    @Autowired
    public KanbanServiceImpl(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }


    @Override

    public List<Employee> getAllEmployee(String userId) throws EmployeeNotFoundException
    {
        Optional<Employee> optionalEmployee= employeeRepository.findById(userId);
        Employee registeredEmployee= optionalEmployee.get();
        if (optionalEmployee.isEmpty())
        {
            throw new EmployeeNotFoundException();
        }
        else {
            return (List<Employee>) registeredEmployee;
        }
    }

    @Override

    public Employee saveEmployeeTaskToTaskList(Task task, String userId) throws EmployeeNotFoundException, TaskAlreadyExistsException
    {
        Optional<Employee> optionalEmployee= employeeRepository.findById(userId);
        if (optionalEmployee.isPresent())
        {
            Employee registeredEmployee= optionalEmployee.get();
            List<Task> taskList= registeredEmployee.getUserTaskList();
            if (taskList == null)
            {
                registeredEmployee.setUserTaskList(Arrays.asList(task));
            }
            else
            {
                boolean taskAlreadyExist = false;
                for (Task taskObj : taskList)
                {
                    if (taskObj.getTaskName().equals(task.getTaskName()))
                    {
                        taskAlreadyExist = true;
                        break;
                    }
                }
                if (!taskAlreadyExist)
                {
                    taskList.add(task);
                    registeredEmployee.setUserTaskList(taskList);
                }
            }
            employeeRepository.save(registeredEmployee);
            optionalEmployee= employeeRepository.findById(userId);
            if (optionalEmployee.isPresent())
            {
                return optionalEmployee.get();
            }
        }
        throw new EmployeeNotFoundException();
    }


    }



