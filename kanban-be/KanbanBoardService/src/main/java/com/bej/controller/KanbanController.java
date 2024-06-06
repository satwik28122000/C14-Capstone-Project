package com.bej.controller;

import com.bej.domain.Task;
import com.bej.exception.EmployeeNotFoundException;
import com.bej.exception.TaskNotFoundException;
import com.bej.service.IKanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class KanbanController {
    private IKanbanService kanbanService;
    @Autowired
    public KanbanController(IKanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    @DeleteMapping("/employee/{userId}/task/{taskId}")
    public void deleteTaskFromEmployee(@PathVariable String userId, @PathVariable String taskId) throws TaskNotFoundException, EmployeeNotFoundException {
        kanbanService.deleteTaskFromEmployee(userId, taskId);
    }

    @GetMapping("/employee/{userId}/tasks")
    public List<Task> getAllEmployeeTaskFromTaskList(@PathVariable String userId) throws EmployeeNotFoundException {
        return kanbanService.getAllEmployeeTaskFromTaskList(userId);
    }
}
