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
import java.util.Optional;
import java.util.stream.Collectors;

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
        return null;
    }

    @Override
    public Employee updateEmployeeTaskInTaskList(String userId, Task task) throws EmployeeNotFoundException, TaskNotFoundException {
        Employee employee1 = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        List<Task> taskList = employee1.getUserTaskList();
        int ind = taskList.indexOf(task);
        if(ind < 0){
            throw new TaskNotFoundException();
        }
        for(Task t: taskList){
            if(t.getTaskId().equals(task.getTaskId())){
                t.setTaskName(task.getTaskName());
                t.setStatus(task.getStatus());
                t.setDueDate(task.getDueDate());
                t.setEmployee(task.getEmployee());
                t.setPriority(task.getPriority());
                t.setTaskdesc(task.getTaskDesc());
            }
        }
        employee1.setUserTaskList(taskList);
        return employeeRepository.save(employee1);

    }

    @Override
    public List<Task> deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(userId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Task> taskList = employee.getUserTaskList();
            boolean taskExists = taskList.stream().anyMatch(task -> task.getTaskId().equals(taskId));
            if (!taskExists) {
                throw new TaskNotFoundException();
            }
            List<Task> updatedTaskList = taskList.stream()
                    .filter(task -> !task.getTaskId().equals(taskId))
                    .collect(Collectors.toList());
            employee.setUserTaskList(updatedTaskList);
            employeeRepository.save(employee);
            return updatedTaskList;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(userId);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get().getUserTaskList();
        } else {
            throw new EmployeeNotFoundException();
        }
    }





}
