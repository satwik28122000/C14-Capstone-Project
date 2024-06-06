package com.bej.service;
import com.bej.domain.Employee;
import com.bej.domain.Task;
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
    public void deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException {
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
