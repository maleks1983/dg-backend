package com.app.dgBackend.service;

import com.app.dgBackend.entity.Operation;
import com.app.dgBackend.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("classpath:operationSqlTest.sql")
@ActiveProfiles("test")
class OperationServiceTest {
    @Autowired
    private OperationService operationService;
    @Autowired
    private OperationRepository operationRepository;

    @Test
    void save() {
        Operation operation = new Operation("Operation 4",4.99);
        Operation op = operationService.save(operation);
        assertEquals("Operation 4", op.getName());
    }

    @Test
    void findAll() {
        assertEquals(3, operationService.findAll().size());
    }

    @Test
    void findById() {
        Operation operation = new Operation(1, "Operation" , 1.99);
        assertEquals(operation, operationService.findById(1));
    }

    @Test
    void delete() {
        operationService.delete(1);
        assertEquals(2, operationRepository.findAll().size());
    }
}