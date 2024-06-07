package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeAlreadyExistsException;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskNotFoundException;
import com.bej.repository.EmployeeRepository;
import com.bej.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanbanServiceImpl implements IKanbanService {

    private EmployeeRepository employeeRepository;
    private ManagerRepository managerRepository;
    @Autowired
    public KanbanServiceImpl(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }


    @Override
    public Employee registerEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        if(employeeRepository.findById(employee.getUserId()).isPresent()){
            throw new EmployeeAlreadyExistsException();
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeTaskInTaskList(String userId, Task task) throws EmployeeNotFoundException, TaskNotFoundException {
        Employee employee1 = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        List<Task> taskList = employee1.getUserTaskList();
        if(taskList == null){
            throw new TaskNotFoundException();
        }

        for(Task t: taskList){
            if(t.getTaskId().equals(task.getTaskId())){
                t.setTaskName(task.getTaskName());
                t.setStatus(task.getStatus());
                t.setDueDate(task.getDueDate());
                t.setPriority(task.getPriority());
                t.setTaskdesc(task.getTaskDesc());
            }
        }
        employee1.setUserTaskList(taskList);
        return employeeRepository.save(employee1);
    }




}
