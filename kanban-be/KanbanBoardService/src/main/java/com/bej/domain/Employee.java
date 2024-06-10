package com.bej.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Employee {
    @Id
    private String userId;
    private String  userName;
    private String  password;
    private String  designation;
    private String  emailId;
    private String managerId;
    private List<Task> userTaskList; //saveTask will add a task in this task list

}
