package com.bej.domain;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Task {
    @Id
    private String taskId;
    private String taskName ;
    private String taskDesc;
    private String status ;
    private String priority;
    private String dueDate;
    private Employee employee;

    public Task() {
    }

    public Task(String taskName, String taskDesc, String status, String priority, String dueDate, Employee employee) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.employee = employee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId() {
        this.taskId = UUID.randomUUID().toString();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskdesc(String taskDescesc) {
        this.taskDesc = taskDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
