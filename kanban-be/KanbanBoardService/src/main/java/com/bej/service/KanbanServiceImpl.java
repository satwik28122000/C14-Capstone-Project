package com.bej.service;

import com.bej.domain.Employee;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.repository.EmployeeRepository;
import com.bej.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Employee registerEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployeeTaskInTaskList(String userId, Employee employee) throws EmployeeNotFoundException {
        //Employee employee1 = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        return null;
    }


}
