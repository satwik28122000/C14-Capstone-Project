package com.bej.domain;

import org.springframework.data.annotation.Id;


public class Task {
    @Id
    private String taskId;
    private String taskName;
    private String taskDesc;
    private String status ;
    private String priority;
    private String dueDate;
    private Employee assignedTo;
    private String projectId;

    public Task(){}
    public Task(String taskId,String taskName, String taskDesc, String status, String priority, String dueDate,Employee assignedTo,String projectId) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.taskId= taskId;
        this.assignedTo=assignedTo;
        this.projectId=projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }
}
