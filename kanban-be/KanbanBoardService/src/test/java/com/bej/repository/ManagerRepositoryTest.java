package com.bej.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ManagerRepositoryTest {

    private ManagerRepository managerRepository;
    @Autowired
    public ManagerRepositoryTest(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {

    }
}
