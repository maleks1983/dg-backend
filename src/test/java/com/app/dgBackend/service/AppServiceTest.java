package com.app.dgBackend.service;

import com.app.dgBackend.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:data.sql")
class AppServiceTest {

    @Autowired
    private AppService appService;

    @Test
    void getProducts() {
        List<Product> p = appService.getProducts();
        assertNotNull(p);
    }
}