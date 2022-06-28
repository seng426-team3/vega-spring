package com.uvic.venus.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uvic.venus.storage.StorageService;

public class FileControllerTests {

    private FileController fileController;

    @BeforeEach
    public void setup() {
        fileController = new FileController();
        fileController.storageService = mock(StorageService.class);
    }
}
