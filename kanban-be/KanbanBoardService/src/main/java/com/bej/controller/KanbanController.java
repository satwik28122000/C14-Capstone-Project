package com.bej.controller;
import com.bej.domain.*;
import com.bej.domain.Task;
import com.bej.exception.*;
import com.bej.service.EmailService;
import com.bej.service.IGenerateEmail;
import com.bej.service.IKanbanService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kanban")
public class KanbanController {

    private IKanbanService kanbanService;
    private IGenerateEmail generateEmail;
    @Autowired
    private EmailService emailService;

    @Autowired
    public KanbanController(IKanbanService kanbanService, IGenerateEmail generateEmail) {
        this.kanbanService = kanbanService;
        this.generateEmail=generateEmail;
    }
   //OpenAPI
    @Operation(summary = "Register employee", description = "This will register new employee")
    @ApiResponse(responseCode = "201", description = "Employee registerd successfully")
    //Register the employee using endpoint "/api/kanban/register
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExistsException {
        try{
            return new ResponseEntity<>(kanbanService.registerEmployee(employee), HttpStatus.CREATED);

        }

        catch (EmployeeAlreadyExistsException enf) {
            throw new EmployeeAlreadyExistsException();
        }

        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Save Manager", description = "This will save manager")
    @ApiResponse(responseCode = "201", description = "manager saved successfully")
    @PostMapping("/managers/saveManager")
    public ResponseEntity createManager(@RequestBody Manager manager){
        try {
            return new ResponseEntity<>(kanbanService.saveManager(manager), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Operation(summary = "Get employee by user id", description = "This will get employee by user id")
//    @ApiResponse(responseCode = "200", description = "Retrive employee successfully")
    @GetMapping("/user/getEmployeesByUserId")
    public ResponseEntity<?> fetchEmployeeByUserId(HttpServletRequest request) throws EmployeeNotFoundException {

        try {
            String userId = getUserIdClaims(request);
            System.out.println("Fetch employee by USERID Claims: "+userId);
            return new ResponseEntity<>(kanbanService.getEmployeeByUserId(userId), HttpStatus.OK);
        }
        catch (EmployeeNotFoundException enf)
        {
            throw new EmployeeNotFoundException();
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Fetch Employee", description = "This will fetch all employee")
    @ApiResponse(responseCode = "200", description = "Employees fetched successfully")
    @GetMapping("/manager/getAllEmployee")
    public ResponseEntity<?> fetchAllEmployee() throws Exception
    {
        try {
            return new ResponseEntity<>(kanbanService.getAllEmployee(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @Operation(summary = "Fetch Manager", description = "This will fetch all manager")
//    @ApiResponse(responseCode = "200", description = "Manager fetched successfully")
    @GetMapping("/getAllManager")
    public ResponseEntity<?> fetchAllManager() throws Exception
    {
        try {
            return new ResponseEntity<>(kanbanService.getAllManager(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "GetAllEmployeeTaskFromTaskList", description = "This will retrive employees task from task list")
    @ApiResponse(responseCode = "200", description = "Employee Task retrived successfully")
    @GetMapping("/user/tasks")
    public ResponseEntity<?> getAllEmployeeTaskFromTaskList(HttpServletRequest request) throws EmployeeNotFoundException
    {
        String userId = getUserIdClaims(request);
        try {
            List<Task> tasks = kanbanService.getAllEmployeeTaskFromTaskList(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save Project in Manager", description = "This will save project in manager")
    @ApiResponse(responseCode = "201", description = "Project saved successfully in manager")
    @PostMapping("/manager/saveProjectInManager")
    public ResponseEntity<?> addManagerProjectToProjectList(@RequestBody Project project,HttpServletRequest request) throws ManagerNotFoundException , ProjectAlreadyExistException
    {
        String managerId = getManagerIdClaims(request);
        try {
            return new ResponseEntity<>(kanbanService.saveProjectInManagerProjectList(project, managerId), HttpStatus.CREATED);
        }
        catch (ManagerNotFoundException mnf)
        {
            throw new ManagerNotFoundException();
        }
        catch (ProjectAlreadyExistException pae)
        {
            throw new ProjectAlreadyExistException();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Save user", description = "This will save new user")
    @ApiResponse(responseCode = "200", description = "User saved successfully")
    //update project in project list
    @PutMapping("/manager/updateproject")
    public ResponseEntity<?> updateProjectInManagerProjectList( @RequestBody Project project,HttpServletRequest request) throws ManagerNotFoundException, ProjectNotFoundException {
        try{
            String managerId = getManagerIdClaims(request);
            return  new ResponseEntity<>(kanbanService.updateProjectInManagerProjectList(managerId,project),HttpStatus.OK);
        }
        catch(ManagerNotFoundException e){
            throw new ManagerNotFoundException();
        }
        catch(ProjectNotFoundException e){
            throw new ProjectNotFoundException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



@Operation(summary = "Get All Project from manager", description = "This will retrive all project from manager")
@ApiResponse(responseCode = "200", description = "Project retrived successfully from manager")
    @GetMapping("/manager/projects")
    public ResponseEntity<?> getAllProjectFromManager(HttpServletRequest request) {
        try {
            String managerId = getManagerIdClaims(request);
            List<Project> projects = kanbanService.getAllProjectFromManager(managerId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (ManagerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modify task in task list of project", description = "This will update task in task list of project")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    //update Task In Task List Of Project
    @PutMapping("/project/{projectId}/task")
    public ResponseEntity<?> modifyTaskInTaskListOfProject(@PathVariable String projectId,@RequestBody Task task) throws TaskNotFoundException, ProjectNotFoundException {
        try{
            return new ResponseEntity<>(kanbanService.updateTaskInProjectTaskList(projectId,task),HttpStatus.OK);
        }
        catch (ProjectNotFoundException e){
            throw new ProjectNotFoundException();
        }
        catch(TaskNotFoundException e){
            throw new TaskNotFoundException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Fetch all task from project", description = "This will get  all task from project ")
    @ApiResponse(responseCode = "200", description = " All task retrived successfully ")
    //get all task from project
    @GetMapping("/project/{projectId}/alltasks")
    public ResponseEntity<?> fetchAllTasksFromProject(@PathVariable String projectId) throws ProjectNotFoundException {
        try{
            return new ResponseEntity<>(kanbanService.getAllTaskFromProject(projectId),HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException();
        }
        catch(Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Save task to project", description = "This will save task to project")
    @ApiResponse(responseCode = "201", description = " Task saved to project successfully")
    @PostMapping("/manager/saveTaskToProject/{projectId}")
    public ResponseEntity<?> addTaskToProject(@PathVariable String projectId, @RequestBody Task task) throws ProjectNotFoundException , TaskAlreadyExistsException
    {
        try {
            return new ResponseEntity<>(kanbanService.saveTaskInProjectTaskList(task , projectId), HttpStatus.CREATED);
        }
        catch (ProjectNotFoundException pnf)
        {
            throw new ProjectNotFoundException();
        }

        catch (TaskAlreadyExistsException tae)
        {
            throw new TaskAlreadyExistsException();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Fetch task by id from project", description = "This will retrived task by id from project")
    @ApiResponse(responseCode = "200", description = "Task retrived successfully")
    @GetMapping("/manager/findTaskByIdFromProject/{projectId}/{taskId}")
    public ResponseEntity<?> fetchTaskByIdFromProject(@PathVariable String taskId , @PathVariable String projectId) throws TaskNotFoundException, ProjectNotFoundException
    {
        try {
            return new ResponseEntity<>(kanbanService.getTaskByIdFromProject(taskId, projectId), HttpStatus.OK);
        }
        catch (TaskNotFoundException tnf)
        {
            throw new TaskNotFoundException();
        }
        catch (ProjectNotFoundException pnf)
        {
            throw new ProjectNotFoundException();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Fetch project by id from manager", description = "This will fetch project by id from manager")
    @ApiResponse(responseCode = "200", description = "retrived project successfully")
    @GetMapping("/manager/{projectId}")
    public ResponseEntity<?> fetchProjectByIdFromManager(@PathVariable String projectId,HttpServletRequest request) throws ProjectNotFoundException, ManagerNotFoundException {
        try{
            String managerId = getManagerIdClaims(request);
            return new ResponseEntity<>(kanbanService.getProjectByIdFromManager(managerId, projectId),HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException();
        } catch (ManagerNotFoundException e) {
            throw new ManagerNotFoundException();
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Fetch task by id from employee", description = "This will fetch task by id from manager")
    @ApiResponse(responseCode = "200", description = "Fetched task from employeee successfully")
    @GetMapping("/user/findTaskByIdFromEmployee/task/{taskId}")
    public ResponseEntity<?> fetchTaskByIdFromEmployee(@PathVariable String taskId,HttpServletRequest request) throws TaskNotFoundException , EmployeeNotFoundException
    {
        try {
            String userId = getUserIdClaims(request);
            return new ResponseEntity<>(kanbanService.getTaskByIdFromEmployee(taskId , userId), HttpStatus.OK);
        }
        catch (TaskNotFoundException tnf)
        {
            throw new TaskNotFoundException();
        }
        catch (EmployeeNotFoundException enf)
        {
            throw new EmployeeNotFoundException();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







    @Operation(summary = "Create task in project and Employee", description = "This will create task in project and employee")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    //Crucial methods for this application
    @PostMapping("/manager/project/{projectId}")
    public ResponseEntity<?> createTaskInProjectAndEmployee(@PathVariable String projectId,@RequestBody Task task){
        try{
            ResponseEntity<?> response= new ResponseEntity<>(kanbanService.saveTaskInProjectAndEmployee(projectId,task),HttpStatus.CREATED);

            return response;
        } catch (ProjectNotFoundException | TaskAlreadyExistsException | EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



























    @Operation(summary = "Modify task in project and employee", description = "This will update task in project and employee")
    @ApiResponse(responseCode = "200", description = "task updated in project and employee successfully")
    @PutMapping("/manager/updateTask/{projectId}")
    public ResponseEntity<?> modifyTaskInProjectAndEmployee(HttpServletRequest request,@PathVariable String projectId,@RequestBody Task task)
            throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException, ManagerNotFoundException {
        try{
            String managerId = getManagerIdClaims(request);
            return new ResponseEntity<>(kanbanService.updateTaskFromManagerToEmployee(managerId,projectId, task),HttpStatus.OK);
        } catch(ManagerNotFoundException e){
            throw  new ManagerNotFoundException();
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException();
        } catch (TaskNotFoundException e){
            throw new TaskNotFoundException();
        } catch( EmployeeNotFoundException e){
            throw new EmployeeNotFoundException();
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modify task in employee to project", description = "This will update task in employee to project")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @PutMapping("/user/updateEmployeeTask")
    public ResponseEntity<?> modifyTaskInEmployeeToProject(HttpServletRequest request,@RequestBody Task task) throws ProjectNotFoundException,
            TaskNotFoundException, EmployeeNotFoundException, ManagerNotFoundException {
        try{
            String userId = getUserIdClaims(request);
            return new ResponseEntity<>(kanbanService.updateTaskFromEmployeeToManager(userId, task),HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException();
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException();
        } catch(ManagerNotFoundException e){
            throw new ManagerNotFoundException();
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Operation(summary = "Save Tak", description = "This will save new task")
//    @ApiResponse(responseCode = "201", description = "Task saved successfully")
    @PostMapping("/manager/project/{projectId}/saveTask")
    public ResponseEntity<?> createTaskInManagerAndEmployee(HttpServletRequest request,@PathVariable String projectId,@RequestBody Task task) throws
            ProjectNotFoundException, ManagerNotFoundException, TaskAlreadyExistsException, EmployeeNotFoundException {
        try{
            String managerId = getManagerIdClaims(request);
            emailService.sendEmail(task.getAssignedTo().getEmailId(),"NEW TASK ASSIGNED",
                    generateEmail.taskAssignEmail(task.getTaskName(),task.getTaskDesc()));
            return new ResponseEntity<>(kanbanService.saveTaskInManagerAndEmployee(managerId,projectId,task),HttpStatus.CREATED);
        } catch(ProjectNotFoundException | ManagerNotFoundException | TaskAlreadyExistsException | EmployeeNotFoundException e){
            throw new RuntimeException(e);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Task  In Project Task List", description = "This will delete task in project tast list")
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    //can be needed or not
    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTaskInProjectTaskList(@PathVariable String projectId, @PathVariable String taskId) {
        try {
            kanbanService.deleteTaskInProjectTaskList(projectId, taskId);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
        }
    }


    public String getUserIdClaims(HttpServletRequest request){
        Claims claim =(Claims) request.getAttribute("userId");
        String userId = claim.getSubject();
        System.out.println(userId);
        return userId;
    }
    public String getManagerIdClaims(HttpServletRequest request){
        Claims claim =(Claims) request.getAttribute("managerId");
        String managerId = claim.getSubject();
        System.out.println(managerId);
        return managerId;
    }
}

