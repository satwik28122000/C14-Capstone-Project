package com.bej.service;
import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.domain.Project;
import com.bej.domain.Task;
import com.bej.exception.*;
import com.bej.repository.EmployeeRepository;
import com.bej.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
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
    public List<Employee> getAllEmployee() throws Exception {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()) {
            throw new Exception();
        }
        return employeeList;
    }



    @Override
    public Employee getEmployeeByUserId(String userId) throws EmployeeNotFoundException
    {
        Optional<Employee> optionalEmployee = employeeRepository.findById(userId);

        if (optionalEmployee.isEmpty())
        {
            throw new EmployeeNotFoundException();
        } else
        {
            return optionalEmployee.get();
        }
    }

    //register employee
    @Override
    public Employee registerEmployee (Employee employee) throws EmployeeAlreadyExistsException {
        if (employeeRepository.findById(employee.getUserId()).isPresent()) {
            throw new EmployeeAlreadyExistsException();
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee saveEmployeeTaskToTaskList(Task task, String userId) throws EmployeeNotFoundException, TaskAlreadyExistsException {


        Optional<Employee> optionalEmployee = employeeRepository.findById(userId);

        if (optionalEmployee.isPresent()) {
            Employee registeredEmployee = optionalEmployee.get();
            List<Task> taskList = registeredEmployee.getUserTaskList();
            if (taskList == null) {
                registeredEmployee.setUserTaskList(Arrays.asList(task));
            }
            else {
                boolean taskAlreadyExist = false;
                for (Task taskObj : taskList) {
                    if (taskObj.getTaskId().equals(task.getTaskId())) {
                        taskAlreadyExist = true;
                        break;
                    }
                }
                if (taskAlreadyExist) {
                   throw new TaskAlreadyExistsException();
                }
                else {
                    taskList.add(task);
                    registeredEmployee.setUserTaskList(taskList);
                }
            }

            employeeRepository.save(registeredEmployee);
            optionalEmployee = employeeRepository.findById(userId);
            if (optionalEmployee.isPresent()) {
                return optionalEmployee.get();

            }
        }
        throw new EmployeeNotFoundException();
    }



    @Override
    public Employee updateEmployeeTaskInTaskList (String userId, Task task) throws EmployeeNotFoundException, TaskNotFoundException
    {
        Employee employee1 = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        List<Task> taskList = employee1.getUserTaskList();
        if (taskList == null) {
            throw new TaskNotFoundException();
        }
        boolean flag = false;
        for(Task t:taskList){
            if(t.getTaskId().equals(task.getTaskId())){
                flag = true;
                break;
            }
        }
        if(!flag){
            throw new TaskNotFoundException();
        }
        for (Task t : taskList) {
            if (t.getTaskId().equals(task.getTaskId())) {
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


@Override
public List<Task> deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException
        {
            Employee employee = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
            List<Task> taskList = employee.getUserTaskList();
            if (taskList == null || taskList.stream().noneMatch(t -> t.getTaskId().equals(taskId))) {
                throw new TaskNotFoundException();
            }

            List<Task> updatedTaskList = taskList.stream().filter(task -> !task.getTaskId().equals(taskId)).collect(Collectors.toList());
            employee.setUserTaskList(updatedTaskList);
            employeeRepository.save(employee);
            return updatedTaskList;
        }
    @Override
    public List<Project> getAllProjectFromManager(String managerId) throws EmployeeNotFoundException
    {
        return managerRepository.findById(managerId)
                .orElseThrow(EmployeeNotFoundException::new)
                .getProjectList();
    }


    @Override
    public List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        return employee.getUserTaskList();
    }

    @Override
    public Manager saveProjectInManagerProjectList(Project project, String managerId) throws ManagerNotFoundException, ProjectAlreadyExistException
    {
        Optional<Manager> optionalManager= managerRepository.findById(managerId);
        if (optionalManager.isPresent())
        {
            Manager registeredManager= optionalManager.get();
            List<Project> projectList= registeredManager.getProjectList();
            if (projectList == null)
            {
                registeredManager.setProjectList(Arrays.asList(project));
            }
            else
            {
                boolean projectAlreadyExist = false;
                for (Project projectObj: projectList)
                {
                    if (projectObj.getProjectId().equals(project.getProjectId()))
                    {
                        projectAlreadyExist = true;
                        break;
                    }
                }
                if (projectAlreadyExist)
                {
                    throw new ProjectAlreadyExistException();
                }
                else
                {
                    projectList.add(project);
                    registeredManager.setProjectList(projectList);
                }
            }
            managerRepository.save(registeredManager);
            optionalManager= managerRepository.findById(managerId);
            if (optionalManager.isPresent())
            {
                return optionalManager.get();
            }
        }
        throw new ManagerNotFoundException();
    }

//    @Override
//    public Manager deleteProjectFromManager(String managerId, String projectId) throws ProjectNotFoundException, ManagerNotFoundException
//    {
//        Optional<Manager> optionalManager= managerRepository.findById(managerId);
//        if (optionalManager.isPresent())
//        {
//            Manager registeredManager= optionalManager.get();
//            List<Project> projectList= registeredManager.getProjectList();
//            boolean projectFound=false;
//            for (Project existingProject : projectList)
//            {
//                if (existingProject.getProjectId().equals(projectId))
//                {
//                    projectFound=true;
//                    projectList.remove(existingProject);
//                    break;
//                }
//            }
//            if (!projectFound)
//            {
//                registeredManager.setProjectList(projectList);
//                return managerRepository.save(registeredManager);
//            }
//            else
//            {
//                throw new ProjectNotFoundException();
//            }
//        }
//        else
//        {
//            throw new ManagerNotFoundException();
//        }
//
//    }

    @Override
    public Manager updateProjectInManagerProjectList(String managerId, Project project) throws ManagerNotFoundException, ProjectNotFoundException {
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);
        List<Project> projectList = manager.getProjectList();
        if(projectList==null){
            throw new ProjectNotFoundException();
        }
        boolean flag = false;
        for(Project p:projectList){
            if(p.getProjectId().equals(project.getProjectId())){
                flag = true;
                break;
            }
        }
        if(!flag){
            throw new ProjectNotFoundException();
        }
        for(Project p:projectList){
            if(p.getProjectId().equals(project.getProjectId())){
                p.setProjectName(project.getProjectName());
                p.setProjectDesc(project.getProjectDesc());
                p.setProjectTasks(project.getProjectTasks());
            }
        }
        manager.setProjectList(projectList);
        return managerRepository.save(manager);
    }


}
