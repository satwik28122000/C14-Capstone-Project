package com.bej.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "managers")
public class Manager {
    @Id
    private String managerId;
    private String managerName;
    private String managerEmail;
    private String managerPassword;
    private List<Project> projectList;

    public Manager(String managerId, String managerName, String managerEmail, String managerPassword, List<Project> projectList) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
        this.managerPassword = managerPassword;
        this.projectList = projectList;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId='" + managerId + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", managerPassword='" + managerPassword + '\'' +
                ", projectList=" + projectList +
                '}';
    }
}
