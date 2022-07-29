package com.uvic.venus.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DemoControllerTests {

    private DemoController demoController;

    @BeforeEach
    public void setup() {
        demoController = new DemoController();
    }

    @Test
    public void testHelloWorld() {
        // Given & When
        String actual = demoController.helloWorld();
        assertEquals("Hello World", actual);
    }
    
}
