package com.seikim.jacoco;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceTest {
    @Autowired
    private JacocoService service;

    @Test
    void goodTest() {
        service.goodMethod(1);
        service.goodMethod(10);
        service.goodMethod(100);
    }

    @Test
    void badTest() {
        service.badMethod(1);
    }
}