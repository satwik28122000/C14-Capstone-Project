package com.bej.controller;
import com.bej.domain.Project;
import com.bej.domain.Task;
import com.bej.domain.Employee;
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



    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> fetchAllEmployee(@RequestBody String userId) throws EmployeeNotFoundException {
        try {
            return new ResponseEntity<>(kanbanService.getAllEmployee(userId), HttpStatus.OK);
        }
        catch (EmployeeNotFoundException enf)
        {
            throw new EmployeeNotFoundException();
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{userId}/tasks")
    public ResponseEntity<?> fetchAllEmployeeTaskFromTaskList(@PathVariable String userId) throws EmployeeNotFoundException
            {
                try {
                    return new ResponseEntity<>(kanbanService.getAllEmployeeTaskFromTaskList(userId), HttpStatus.OK);
                } catch (EmployeeNotFoundException enf) {
                    throw new EmployeeNotFoundException();
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


    @PostMapping("/saveTask")
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
}

