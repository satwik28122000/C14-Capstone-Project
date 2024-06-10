package com.bej.controller;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import com.bej.domain.Project;
import com.bej.domain.Task;
import com.bej.exception.*;
import com.bej.service.IKanbanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KanbanControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IKanbanService kanbanService;

    @InjectMocks
    private KanbanController kanbanController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(kanbanController).build();
    }


    @Test
    public void givenEmployeeWhenRegisterEmployeeThenReturnCreated() throws Exception {
        Employee employee = new Employee("11", "Anuja", "D123", "designation3", "Anuja@gmail.com","Sangeetha@123", null);
        when(kanbanService.registerEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/kanban/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenProjectIdAndTaskIdWhenDeleteTaskInProjectThenReturnOk() throws Exception {
        mockMvc.perform(delete("/api/kanban/projects/1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void givenProjectIdWhenGetAllTasksInProjectThenReturnTasks() throws Exception {
        List<Task> tasks = Arrays.asList(
                new Task("112", "codingFrontpage", "description", "Progress", "midrate", "23-6-2024", null,"123"),
                new Task("113", "codingFrontpage", "description", "Progress", "midrate", "23-6-2024", null,"123")
        );
        when(kanbanService.getAllTaskFromProject(anyString())).thenReturn(tasks);

        mockMvc.perform(get("/api/kanban/project/1/alltasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}