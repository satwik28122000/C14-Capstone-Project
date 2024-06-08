package com.bej.service;
import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.domain.Project;
import com.bej.domain.Task;
import com.bej.exception.*;

import com.bej.exception.EmployeeAlreadyExistsException;

import com.bej.exception.EmployeeNotFoundException;

import java.util.List;



public interface IKanbanService {


    List<Employee> getAllEmployee() throws Exception;
    Employee getEmployeeByUserId(String userId) throws EmployeeNotFoundException;
    Employee registerEmployee(Employee employee) throws EmployeeAlreadyExistsException;
    Employee updateEmployeeTaskInTaskList(String userId, Task employee) throws EmployeeNotFoundException,TaskNotFoundException;

    Employee saveEmployeeTaskToTaskList(Task task, String userId) throws EmployeeNotFoundException, TaskAlreadyExistsException;
    List<Task> deleteTaskFromEmployee(String userId, String taskId) throws TaskNotFoundException, EmployeeNotFoundException;

    List<Task> getAllEmployeeTaskFromTaskList(String userId) throws EmployeeNotFoundException;

    List<Project> getAllProjectFromManager(String managerId) throws ManagerNotFoundException;

    Manager updateProjectInManagerProjectList(String managerId, Project project) throws ManagerNotFoundException, ProjectNotFoundException;
    Manager saveProjectInManagerProjectList(Project project , String managerId) throws ManagerNotFoundException , ProjectAlreadyExistException;
    void deleteTaskInProjectTaskList(String projectId, String taskId) throws ProjectNotFoundException;
   // Manager deleteProjectFromManager(String managerId , String projectId) throws ProjectNotFoundException , ManagerNotFoundException;

    Project updateTaskInProjectTaskList(String projectId,Task task) throws ProjectNotFoundException, TaskNotFoundException;
    List<Task> getAllTaskFromProject(String projectId) throws ProjectNotFoundException;

    Project saveTaskInProjectTaskList(Task task , String projectId) throws  TaskAlreadyExistsException , ProjectNotFoundException;
    Task getTaskByIdFromProject(String taskId , String projectId) throws TaskNotFoundException , ProjectNotFoundException;
    Project getProjectByIdFromManager(String managerId,String projectId) throws ManagerNotFoundException, ProjectNotFoundException;
}

