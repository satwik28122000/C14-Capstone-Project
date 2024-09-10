package com.bej.service;

import org.springframework.stereotype.Service;

@Service
public class GenerateEmailImpl implements IGenerateEmail{
    @Override
    public String taskAssignEmail(String taskName, String taskDesc) {
        System.out.println("Task assign email is invoking");
        String body = String.format("Dear employee a task has been assigned to you\n Task Name: "+taskName+"\nTask description: "+taskDesc);
        return body;
    }
}
