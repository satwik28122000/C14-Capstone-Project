package com.bej.controller;

import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeAlreadyExistsException;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskNotFoundException;
import com.bej.service.IKanbanService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Register the employee using endpoint "/api/kanban/register"
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExistsException {
        try{
            return new ResponseEntity<>(kanbanService.registerEmployee(employee), HttpStatus.CREATED);
        }
        catch(EmployeeAlreadyExistsException e){
            throw new EmployeeAlreadyExistsException();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
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
}
