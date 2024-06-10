package com.bej.service;
import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.domain.Project;
import com.bej.domain.Task;
import com.bej.exception.*;
//import com.bej.proxy.ManagerProxy;
import com.bej.proxy.UserProxy;
import com.bej.repository.EmployeeRepository;
import com.bej.repository.ManagerRepository;
import com.bej.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class KanbanServiceImpl implements IKanbanService {

    private EmployeeRepository employeeRepository;
    private ManagerRepository managerRepository;
    private ProjectRepository projectRepository;
    private UserProxy userProxy;
   // private ManagerProxy managerProxy;
    @Autowired
    public KanbanServiceImpl(UserProxy userProxy,EmployeeRepository employeeRepository, ManagerRepository managerRepository,ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.userProxy=userProxy;
       // this.managerProxy=managerProxy;
    }

    //done

    @Override
    public List<Employee> getAllEmployee() throws Exception {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()) {
            throw new Exception();
        }
        return employeeList;
    }

    //done

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

    //done

    //register employee in employee db
//    @Override
//    public Employee registerEmployee (Employee employee) throws EmployeeAlreadyExistsException {
//        if (employeeRepository.findById(employee.getUserId()).isPresent()) {
//            throw new EmployeeAlreadyExistsException();
//        }
//        Employee saveEmp=employeeRepository.save(employee);
//        if(!(saveEmp.getUserId().isEmpty()))
//        {
//            ResponseEntity<?> res=userProxy.saveUser(employee);
//            System.out.println(res.getBody());
//        }
//        return saveEmp;
//    }

    //done

    @Override
    public Employee registerEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        if (employeeRepository.findById(employee.getUserId()).isPresent()) {
            throw new EmployeeAlreadyExistsException();
        }
        Employee savedEmployee = employeeRepository.save(employee);

        // Perform null check before invoking methods on userProxy
        if (userProxy != null && !savedEmployee.getUserId().isEmpty()) {
            ResponseEntity<?> responseEntity = userProxy.saveUser(employee);
            // Process the response if needed
            System.out.println(responseEntity.getBody());
        }

        return savedEmployee;
    }

    //done

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

           return employeeRepository.save(registeredEmployee);

        }
        throw new EmployeeNotFoundException();
    }


    //update task in task list of employee document

    //done

    @Override
    public Employee updateEmployeeTaskInTaskList (String userId, Task task) throws EmployeeNotFoundException, TaskNotFoundException
    {
        Employee employee1 = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        List<Task> taskList = employee1.getUserTaskList();
        if (taskList == null || taskList.isEmpty()) {
            throw new TaskNotFoundException();
        }
        boolean flag = false;

        for (Task t : taskList) {
            if (t.getTaskId().equals(task.getTaskId())) {
                t.setTaskName(task.getTaskName());
                t.setStatus(task.getStatus());
                t.setDueDate(task.getDueDate());
                t.setPriority(task.getPriority());
                t.setTaskDesc(task.getTaskDesc());
                t.setAssignedTo(task.getAssignedTo());
                flag = true;
                break;
            }
        }
        if(!flag){
            throw new TaskNotFoundException();
        }
        employee1.setUserTaskList(taskList);
        return employeeRepository.save(employee1);
    }

    //done

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
    public List<Project> getAllProjectFromManager(String managerId) throws ManagerNotFoundException
    {
        return managerRepository.findById(managerId)
                .orElseThrow(ManagerNotFoundException::new)
                .getProjectList();
    }





    //done
    @Override
    public List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(userId).orElseThrow(EmployeeNotFoundException::new);
        return employee.getUserTaskList();
    }

    @Override
    public Manager saveProjectInManagerProjectList(Project project, String managerId) throws ManagerNotFoundException, ProjectAlreadyExistException
    {
        Manager manager= managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);

            List<Project> projectList= manager.getProjectList();
            if (projectList == null)
            {
                manager.setProjectList(Arrays.asList(project));
            }
            else
            {
                for (Project projectObj: projectList)
                {
                    if (projectObj.getProjectId().equals(project.getProjectId()))
                    {
                        throw new ProjectAlreadyExistException();
                    }
                }
                projectList.add(project);
                manager.setProjectList(projectList);
                Project p = projectRepository.save(project);
            }

            return managerRepository.save(manager);
    }

    //done

    @Override
    public void deleteTaskInProjectTaskList(String projectId, String taskId) throws ProjectNotFoundException {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException());
        project.getProjectTasks().removeIf(task -> task.getTaskId().equals(taskId));
        projectRepository.save(project);
    }



    //update project in project list of manager
    @Override
    public Manager updateProjectInManagerProjectList(String managerId, Project project) throws ManagerNotFoundException, ProjectNotFoundException {
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);
        List<Project> projectList = manager.getProjectList();
        if(projectList==null || projectList.isEmpty()){
            throw new ProjectNotFoundException();
        }
        boolean flag = false;

        for(Project p:projectList){
            if(p.getProjectId().equals(project.getProjectId())){
                p.setProjectName(project.getProjectName());
                p.setProjectDesc(project.getProjectDesc());
                p.setProjectTasks(project.getProjectTasks());
                flag = true;
                break;
            }
        }
        if(!flag){
            throw new ProjectNotFoundException();
        }
        manager.setProjectList(projectList);
        return managerRepository.save(manager);
    }

    //update task in project task list
    @Override
    public Project updateTaskInProjectTaskList(String projectId,Task task) throws ProjectNotFoundException, TaskNotFoundException {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        List<Task> taskList = project.getProjectTasks();
        if(taskList == null || taskList.isEmpty()){
            throw new ProjectNotFoundException();
        }
        boolean flag = false;
        for (Task t : taskList) {
            if (t.getTaskId().equals(task.getTaskId())) {
                // Task found, update its details
                t.setTaskName(task.getTaskName());
                t.setStatus(task.getStatus());
                t.setDueDate(task.getDueDate());
                t.setPriority(task.getPriority());
                t.setTaskDesc(task.getTaskDesc());
                t.setAssignedTo(task.getAssignedTo());
                flag = true;
                break;
            }
        }
        if(!flag){
            throw new TaskNotFoundException();
        }
        project.setProjectTasks(taskList);
        return projectRepository.save(project);
    }

    // get task list from project
    @Override
    public List<Task> getAllTaskFromProject(String projectId) throws ProjectNotFoundException {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        List<Task> taskList = project.getProjectTasks();

        if (taskList == null) {
            return new ArrayList<>();  // Return an empty list if taskList is null
        }

        // Return the list of tasks
        return taskList;
    }

    @Override
    public Project saveTaskInProjectTaskList(Task task, String projectId) throws  TaskAlreadyExistsException, ProjectNotFoundException
    {
        Project project= projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        List<Task> taskList= project.getProjectTasks();
        if (taskList == null)
        {
            project.setProjectTasks(Arrays.asList(task));
        }
        else
        {
            for (Task taskObj : taskList)
            {
                if (taskObj.getTaskId().equals(task.getTaskId()))
                {
                    throw new TaskAlreadyExistsException();
                }
            }
            taskList.add(task);
            project.setProjectTasks(taskList);

        }
        return projectRepository.save(project);
    }

    @Override
    public Task getTaskByIdFromProject(String taskId, String projectId) throws TaskNotFoundException, ProjectNotFoundException {

        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        List<Task> taskList = project.getProjectTasks();
        if (taskList == null || taskList.isEmpty()) {
            throw new TaskNotFoundException();
        }
        for (Task task : taskList) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public Project getProjectByIdFromManager(String managerId,String projectId) throws ManagerNotFoundException, ProjectNotFoundException {
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);
        List<Project> projectList = manager.getProjectList();
        if(projectList==null || projectList.isEmpty()){
            throw new ProjectNotFoundException();
        }
        for(Project project:projectList){
            if(project.getProjectId().equals(projectId)){
                return project;
            }
        }
        throw new ProjectNotFoundException();
    }

    @Override
    public Task getTaskByIdFromEmployee(String taskId, String userId) throws TaskNotFoundException, EmployeeNotFoundException
    {
        Employee employee = employeeRepository.findById(userId).orElseThrow( EmployeeNotFoundException::new);
        List<Task> taskList = employee.getUserTaskList();
        if (taskList == null || taskList.isEmpty())
        {
            throw new TaskNotFoundException();
        }
        for (Task task : taskList)
        {
            if (task.getTaskId().equals(taskId))
            {
                return task;
            }
        }

        throw new TaskNotFoundException();
    }

    //done
    @Override
    public Manager saveManager(Manager manager){
       // return managerRepository.save(manager);
        Manager savedManager = managerRepository.save(manager);
        if (userProxy != null && !savedManager.getManagerId().isEmpty()) {
            ResponseEntity<?> responseEntity = userProxy.createManager(manager);
            System.out.println(responseEntity.getBody());
        }
        return savedManager;
    }


    //no need to do testing
    //save task in manager task as well as employee task list
    @Override
    public Task saveTaskInProjectAndEmployee(String projectId, Task task) throws ProjectNotFoundException, TaskAlreadyExistsException, EmployeeNotFoundException {
        Project project = saveTaskInProjectTaskList(task,projectId);
        Employee employee = saveEmployeeTaskToTaskList(task, task.getAssignedTo().getUserId());
        return task;
    }

    //no need to do testing

    //update task in manager task to employee task list
    @Override
    public Task updateTaskFromManagerToEmployee(String projectId,Task task) throws ProjectNotFoundException, TaskNotFoundException,EmployeeNotFoundException
    {
        updateTaskInProjectTaskList(projectId, task);
        updateEmployeeTaskInTaskList(task.getAssignedTo().getUserId(),task);
        return task;
    }
    //no need to do testing

    //save task in employee task to manager task list
    @Override
    public Task updateTaskFromEmployeeToManager(String userId, Task task) throws TaskNotFoundException, EmployeeNotFoundException, ProjectNotFoundException {
        updateEmployeeTaskInTaskList(userId,task);
        updateTaskInProjectTaskList(task.getProjectId(),task);
        return task;
    }


}
