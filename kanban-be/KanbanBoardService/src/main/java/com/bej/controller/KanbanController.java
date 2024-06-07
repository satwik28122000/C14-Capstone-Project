package com.bej.controller;
import com.bej.domain.*;
import com.bej.domain.Task;
import com.bej.exception.*;
import com.bej.service.IKanbanService;
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



    @GetMapping("/getEmployeesByUserId/{userId}")
    public ResponseEntity<?> fetchEmployeeByUserId(@PathVariable String userId, @RequestBody Employee employee) throws EmployeeNotFoundException {
        try {
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

    @GetMapping("/getAllEmployee")
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

    @GetMapping("/employee/{userId}/tasks")
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


    @PostMapping("/saveTaskInEmployee")
    public ResponseEntity<?> addEmployeeTaskToTaskList(@RequestBody Task task, String userId) throws EmployeeNotFoundException , TaskAlreadyExistsException{
        try {

            return new ResponseEntity<>(kanbanService.saveEmployeeTaskToTaskList(task, userId), HttpStatus.CREATED);
        }

        catch (EmployeeNotFoundException enf)
        {
            throw new EmployeeNotFoundException();
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


    @PostMapping("/saveProjectInManager")
    public ResponseEntity<?> addManagerProjectToProjectList(@RequestBody Project project, String managerId) throws ManagerNotFoundException , ProjectAlreadyExistException
    {
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

    //update task in task list in employee using endpoint "/api/kanban/{userid}"
    @PutMapping("/updatetask/{userid}")
    public ResponseEntity<?> updateEmployeeTaskInTaskList(@PathVariable String userid,@RequestBody Task task) throws EmployeeNotFoundException, TaskNotFoundException {
        try{
            return new ResponseEntity<>(kanbanService.updateEmployeeTaskInTaskList(userid,task),HttpStatus.OK);
        }
        catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        }
        catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //update project in project list
    @PutMapping("/updateproject/{managerid}")
    public ResponseEntity<?> updateProjectInManagerProjectList(@PathVariable String managerid, @RequestBody Project project) throws ManagerNotFoundException, ProjectNotFoundException {
        try{
            return  new ResponseEntity<>(kanbanService.updateProjectInManagerProjectList(managerid,project),HttpStatus.OK);
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

    @DeleteMapping("/deleteTask/{userId}/{taskId}")
    public ResponseEntity<?> deleteTaskFromEmployee(@PathVariable String userId, @PathVariable String taskId) {
        try {
            List<Task> updatedTaskList = kanbanService.deleteTaskFromEmployee(userId, taskId);
            return new ResponseEntity<>(updatedTaskList, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/manager/{managerId}/projects")
    public ResponseEntity<?> getAllProjectFromManager(@PathVariable String managerId) {
        try {
            List<Project> projects = kanbanService.getAllProjectFromManager(managerId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

