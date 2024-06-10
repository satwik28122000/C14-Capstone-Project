package com.bej.controller;
import com.bej.domain.*;
import com.bej.domain.Task;
import com.bej.exception.*;
import com.bej.service.IKanbanService;
import io.jsonwebtoken.Claims;
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

    @Autowired
    public KanbanController(IKanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

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

    @PostMapping("/managers/saveManager")
    public ResponseEntity createManager(@RequestBody Manager manager){
        try {
            return new ResponseEntity(kanbanService.saveManager(manager), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/user/getEmployeesByUserId")
    public ResponseEntity<?> fetchEmployeeByUserId(HttpServletRequest request) throws EmployeeNotFoundException {

        try {
            String userId = getUserIdClaims(request);
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

    @GetMapping("/manager/employees/{userId}/tasks")
    public ResponseEntity<?> getAllEmployeeTaskFromTaskList(@PathVariable String userId) throws EmployeeNotFoundException
            {
                try {
                    List<Task> tasks = kanbanService.getAllEmployeeTaskFromTaskList(userId);
                    return new ResponseEntity<>(tasks, HttpStatus.OK);
                } catch (EmployeeNotFoundException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                } catch (Exception e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }



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


//    @DeleteMapping("/deleteProject/{projectId}")
//    public ResponseEntity<?> deleteProject(@PathVariable String projectId , @RequestBody String managerId) throws ManagerNotFoundException , ProjectNotFoundException
//    {
//        try {
//            return new ResponseEntity<>(kanbanService.deleteProjectFromManager(projectId , managerId), HttpStatus.OK);
//        }
//        catch (ManagerNotFoundException mnf)
//        {
//            throw new ManagerNotFoundException();
//        }
//        catch (ProjectNotFoundException pnf)
//        {
//            throw new ProjectNotFoundException();
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

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


    //Crucial methods for this application
    @PostMapping("/manager/project/{projectId}")
    public ResponseEntity<?> createTaskInProjectAndEmployee(@PathVariable String projectId,@RequestBody Task task){
        try{
            return new ResponseEntity<>(kanbanService.saveTaskInProjectAndEmployee(projectId,task),HttpStatus.CREATED);
        } catch (ProjectNotFoundException | TaskAlreadyExistsException | EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/manager/updateTask/{projectId}")
    public ResponseEntity<?> modifyTaskInProjectAndEmployee(@PathVariable String projectId,@RequestBody Task task)
    throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException
    {
        try{
            return new ResponseEntity<>(kanbanService.updateTaskFromManagerToEmployee(projectId, task),HttpStatus.OK);
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

    @PutMapping("/user/updateEmployeeTask")
    public ResponseEntity<?> modifyTaskInEmployeeToProject(HttpServletRequest request,@RequestBody Task task) throws ProjectNotFoundException,
            TaskNotFoundException, EmployeeNotFoundException {
        try{
            String userId = getManagerIdClaims(request);
            return new ResponseEntity<>(kanbanService.updateTaskFromEmployeeToManager(userId, task),HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException();
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException();
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




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

