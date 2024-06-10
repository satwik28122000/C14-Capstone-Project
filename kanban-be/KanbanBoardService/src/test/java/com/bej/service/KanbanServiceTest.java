package com.bej.service;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.domain.Project;
import com.bej.domain.Task;
import com.bej.exception.*;
import com.bej.repository.EmployeeRepository;
import com.bej.repository.ManagerRepository;
import com.bej.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class KanbanServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ManagerRepository managerRepository;
    @InjectMocks
    private KanbanServiceImpl kanbanService;
    private Employee employee1;
    private Employee employee2;
    private Manager manager;
    private Task task1;
    private Task task2;
    private Project project1;
    private Project project2;
    private List<Task> taskList;
    private List<Project> projectList;

    @BeforeEach
    void setUp() {
        task1=new Task("101","Generate token","Generate token in user auth service","Assigned","High","09-06-2024",employee1,"12321");
        task2=new Task("102","Kanban Service","Implement register employee method","Assigned","High","10-06-2024",employee2,"12321");
        employee1 = new Employee("pallavi@12","Pallavi","qwerty123", "Full Stack Developer","pallavi@gmail.com",taskList);
        employee2 = new Employee("priya@12","Priyanka","qwerty123", "Full Stack Developer","priya@gmail.com",taskList);
        manager = new Manager("Satwik123", "Satwik Banerjee", "satwik@gmail.com", "abcd", projectList);

    }

    @AfterEach
    void tearDown() {
        task1=null;
        task2=null;
        employee1=null;
        employee2=null;
        manager=null;
        project1=null;
        project2=null;
        employeeRepository.deleteAll();
    }



    @Test

    public void givenEmployeeToSaveReturnSavedEmployeeSuccess() throws EmployeeAlreadyExistsException
    {
        Mockito.when(employeeRepository.findById(employee1.getUserId())).thenReturn(Optional.ofNullable(null));
        Mockito.when(employeeRepository.save(any())).thenReturn(employee1);
        assertEquals(employee1, kanbanService.registerEmployee(employee1));
        verify(employeeRepository, times(1)).save(any());
        verify(employeeRepository, times(1)).findById(any());
    }

    @Test

    public void givenEmployeeToSaveReturnSavedEmployeeFailure()
    {
        Mockito.when(employeeRepository.findById(employee1.getUserId())).thenReturn(Optional.ofNullable(employee1));
        assertThrows(EmployeeAlreadyExistsException.class,()->kanbanService.registerEmployee(employee1));
        verify(employeeRepository,times(0)).save(any());
        verify(employeeRepository, times(1)).findById(any());

    }

    @Test
    public void testGetAllEmployeeSuccess() throws Exception {

        Employee employee1 = new Employee();
        employee1.setUserId("pallavi@12");
        Employee employee2 = new Employee();
        employee2.setUserId("priya@12");
        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> result = kanbanService.getAllEmployee();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(employeeList, result);
        verify(employeeRepository).findAll();
    }

    @Test
    public void testGetAllEmployeeFailure() {

        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> {
            kanbanService.getAllEmployee();
        });

        verify(employeeRepository).findAll();
    }


    @Test
    public void testGetEmployeeByUserIdSuccess() throws EmployeeNotFoundException {
        String userId = "pallavi@12";
        Employee employee = new Employee();
        employee.setUserId(userId);
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));

        Employee result = kanbanService.getEmployeeByUserId(userId);
        assertNotNull(result);
        assertEquals(employee, result);
        verify(employeeRepository).findById(userId);
    }

    @Test
    public void testGetEmployeeByUserIdNotFound() {
        String userId = "pallavi@12";
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> {
            kanbanService.getEmployeeByUserId(userId);
        });

        verify(employeeRepository).findById(userId);
    }

    @Test
    public void testUpdateEmployeeTaskInTaskListSuccess() throws EmployeeNotFoundException, TaskNotFoundException {

        String userId = "pallavi@12";
        Task existingTask = new Task();
        existingTask.setTaskId("task1");
        existingTask.setTaskName("Old Task Name");

        Task updatedTask = new Task();
        updatedTask.setTaskId("task1");
        updatedTask.setTaskName("New Task Name");

        Employee employee = new Employee();
        employee.setUserId(userId);
        List<Task> taskList = new ArrayList<>();
        taskList.add(existingTask);
        employee.setUserTaskList(taskList);

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = kanbanService.updateEmployeeTaskInTaskList(userId, updatedTask);

        assertNotNull(result);
        assertEquals("New Task Name", result.getUserTaskList().get(0).getTaskName());
        verify(employeeRepository).findById(userId);
        verify(employeeRepository).save(employee);
    }

    @Test
    public void testUpdateEmployeeTaskInTaskListEmployeeNotFound() {
        String userId = "pallavi@12";
        Task task = new Task();
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> {
            kanbanService.updateEmployeeTaskInTaskList(userId, task);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void testUpdateEmployeeTaskInTaskListTaskNotFound() {

        String userId = "pallavi@12";
        Task task = new Task();
        task.setTaskId("task1");

        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setUserTaskList(new ArrayList<>());

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));

        assertThrows(TaskNotFoundException.class, () -> {
            kanbanService.updateEmployeeTaskInTaskList(userId, task);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void testUpdateEmployeeTaskInTaskListTaskNotFoundInList() {
        String userId = "pallavi@12";
        Task existingTask = new Task();
        existingTask.setTaskId("task1");

        Task updatedTask = new Task();
        updatedTask.setTaskId("task2");

        Employee employee = new Employee();
        employee.setUserId(userId);
        List<Task> taskList = new ArrayList<>();
        taskList.add(existingTask);
        employee.setUserTaskList(taskList);

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));
        assertThrows(TaskNotFoundException.class, () -> {
            kanbanService.updateEmployeeTaskInTaskList(userId, updatedTask);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    @Test
    public void testDeleteTaskFromEmployeeSuccess() throws EmployeeNotFoundException, TaskNotFoundException {

        String userId = "pallavi@12";
        String taskId = "task1";
        Task task1 = new Task();
        task1.setTaskId(taskId);
        Task task2 = new Task();
        task2.setTaskId("task2");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setUserTaskList(taskList);

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));
        List<Task> result = kanbanService.deleteTaskFromEmployee(userId, taskId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.stream().anyMatch(t -> t.getTaskId().equals(taskId)));
        verify(employeeRepository).findById(userId);
        verify(employeeRepository).save(employee);
    }

    @Test
    public void testDeleteTaskFromEmployeeEmployeeNotFound() {

        String userId = "pallavi@12";
        String taskId = "task1";
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> {
            kanbanService.deleteTaskFromEmployee(userId, taskId);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void testDeleteTaskFromEmployeeTaskNotFound() {
        String userId = "pallavi@12";
        String taskId = "task1";

        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setUserTaskList(new ArrayList<>());

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));

        assertThrows(TaskNotFoundException.class, () -> {
            kanbanService.deleteTaskFromEmployee(userId, taskId);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void testDeleteTaskFromEmployeeTaskNotInList()
    {
        String userId = "pallavi@12";
        String taskId = "task1";
        Task task1 = new Task();
        task1.setTaskId("task2");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);

        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setUserTaskList(taskList);

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));

        assertThrows(TaskNotFoundException.class, () -> {
            kanbanService.deleteTaskFromEmployee(userId, taskId);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }



    @Test
    public void testDeleteTaskInProjectTaskListSuccess() throws ProjectNotFoundException {
        // Arrange
        String projectId = "project1";
        String taskId = "task1";

        Task task1 = new Task();
        task1.setTaskId(taskId);
        Task task2 = new Task();
        task2.setTaskId("task2");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectTasks(taskList);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        kanbanService.deleteTaskInProjectTaskList(projectId, taskId);

        // Assert
        assertEquals(1, project.getProjectTasks().size());
        assertFalse(project.getProjectTasks().stream().anyMatch(t -> t.getTaskId().equals(taskId)));
        verify(projectRepository).findById(projectId);
        verify(projectRepository).save(project);
    }

    @Test
    public void testDeleteTaskInProjectTaskListProjectNotFound() {
        // Arrange
        String projectId = "project1";
        String taskId = "task1";
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProjectNotFoundException.class, () -> {
            kanbanService.deleteTaskInProjectTaskList(projectId, taskId);
        });

        verify(projectRepository).findById(projectId);
        verify(projectRepository, never()).save(any(Project.class));
    }


    @Test
    public void testSaveEmployeeTaskToTaskListSuccess() throws EmployeeNotFoundException, TaskAlreadyExistsException {

        String userId = "pallavi@12";
        Task task = new Task();
        task.setTaskId("task1");
        Employee employee = new Employee();
        employee.setUserId(userId);
        List<Task> taskList = new ArrayList<>();
        employee.setUserTaskList(taskList);
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = kanbanService.saveEmployeeTaskToTaskList(task, userId);
        assertNotNull(result);
        assertEquals(1, result.getUserTaskList().size());
        assertTrue(result.getUserTaskList().contains(task));
        verify(employeeRepository).findById(userId);
        verify(employeeRepository).save(employee);
    }

    @Test
    public void testSaveEmployeeTaskToTaskListEmployeeNotFound() {
        String userId = "pallavi@12";
        Task task = new Task();
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> {
            kanbanService.saveEmployeeTaskToTaskList(task, userId);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void testSaveEmployeeTaskToTaskListTaskAlreadyExists() {

        String userId = "pallavi@12";
        Task task = new Task();
        task.setTaskId("task1");

        Employee employee = new Employee();
        employee.setUserId(userId);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        employee.setUserTaskList(taskList);

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));
        assertThrows(TaskAlreadyExistsException.class, () -> {
            kanbanService.saveEmployeeTaskToTaskList(task, userId);
        });

        verify(employeeRepository).findById(userId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    @Test
    public void getAllEmployeeTaskFromTaskList_Success() throws EmployeeNotFoundException {
        // Arrange
        String userId = "pallavi@12";
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setUserTaskList(tasks);

        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.of(employee));

        // Act
        List<Task> result = kanbanService.getAllEmployeeTaskFromTaskList(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(tasks));
    }

    @Test
    public void getAllEmployeeTaskFromTaskList_NotFound() {
        // Arrange
        String userId = "pallavi@12";
        Mockito.when(employeeRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> kanbanService.getAllEmployeeTaskFromTaskList(userId));
    }


    @Test
    void testSaveManager() {
        when(managerRepository.save(any(Manager.class))).thenReturn(manager);
        Manager savedManager = kanbanService.saveManager(manager);
        verify(managerRepository).save(manager);
        assertEquals(manager.getManagerId(), savedManager.getManagerId());
        assertEquals(manager.getManagerPassword(), savedManager.getManagerPassword());
    }

    @Test
    void testDeleteTaskFromEmployee() throws EmployeeNotFoundException, TaskNotFoundException {
        when(employeeRepository.findById("pallavi@12")).thenReturn(Optional.of(employee1));

        List<Task> updatedTaskList = kanbanService.deleteTaskFromEmployee("pallavi@12", "101");

        verify(employeeRepository).save(employee1);
        assertEquals(1, updatedTaskList.size());
        assertEquals("task2", updatedTaskList.get(0).getTaskId());
    }


    @Test
    void testDeleteTaskFromEmployee_TaskNotFound() {
        when(employeeRepository.findById("user1")).thenReturn(Optional.of(employee2));

        assertThrows(TaskNotFoundException.class, () -> {
            kanbanService.deleteTaskFromEmployee("user1", "task3");
        });
    }

    @Test
    void testDeleteTaskFromEmployee_EmployeeNotFound() {
        when(employeeRepository.findById("user1")).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> {
            kanbanService.deleteTaskFromEmployee("user1", "task1");
        });
    }
}



