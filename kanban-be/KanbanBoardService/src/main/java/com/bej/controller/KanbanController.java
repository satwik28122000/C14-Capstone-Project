package com.bej.controller;
import com.bej.domain.Task;
import com.bej.domain.Employee;
import com.bej.domain.Task;
import com.bej.exception.EmployeeAlreadyExistsException;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskAlreadyExistsException;
import com.bej.exception.TaskNotFoundException;
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
}

