package com.bej.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;
@Document
public class Project {
    @Id
    private String projectId;
    private String projectName;
    private String projectDesc;
    private List<Task> projectTasks;

    public Project(){}

    public Project(String projectId,String projectName, String projectDesc, List<Task> projectTasks) {
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.projectTasks = projectTasks;
        this.projectId=projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public List<Task> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<Task> projectTasks) {
        this.projectTasks = projectTasks;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectTasks=" + projectTasks +
                '}';
    }
}
