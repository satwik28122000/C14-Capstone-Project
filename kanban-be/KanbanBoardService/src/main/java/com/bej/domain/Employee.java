package com.bej.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Employee {
    @Id
    private String userId;
    private String  userName;
    private String  password;
    private String  designation;
    private String  emailId;
    private List<Task> userTaskList; //saveTask will add a task in this task list

    public Employee() {
    }

    public Employee(String userId, String userName, String password, String designation, String emailId, List<Task> userTaskList) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.designation = designation;
        this.emailId = emailId;
        this.userTaskList = userTaskList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<Task> getUserTaskList() {
        return userTaskList;
    }

    public void setUserTaskList(List<Task> userTaskList) {
        this.userTaskList = userTaskList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", designation='" + designation + '\'' +
                ", emailId='" + emailId + '\'' +
                ", userTaskList=" + userTaskList +
                '}';
    }
}
