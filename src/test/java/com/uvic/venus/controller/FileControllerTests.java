package com.uvic.venus.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.uvic.venus.storage.StorageService;

public class FileControllerTests {

    private FileController fileController;

    @BeforeEach
    public void setup() {
        fileController = spy(FileController.class);
        fileController.storageService = mock(StorageService.class);
    }

    @Test
    public void testListUploadFiles() {
        // Given
        List<String> uploadedFilesList = new ArrayList<>();
        uploadedFilesList.add("file1.txt");
        uploadedFilesList.add("file2.txt");

        doReturn(uploadedFilesList).when(fileController).loadAllUploadedFiles();

        // When
        ResponseEntity<List<String>> actualResult = fileController.listUploadedFiles();

        // Then
        assertEquals(uploadedFilesList, actualResult.getBody());
    }

    @Test
    public void testServeFileWithValidFilename() throws Exception {
        // Given
        Resource resource = new FileUrlResource("testlocation");
        when(fileController.storageService.loadAsResource(any())).thenReturn(resource);

        // When
        ResponseEntity<Resource> response = fileController.serveFile("file1.txt");

        // Then
        assertEquals(resource, response.getBody());
    }

    @Test
    public void testLoadAllUploadedFiles() {
        // Given
        List<Path> pathsList = new ArrayList<>();
        Path outerFilepath = mock(Path.class);
        Path innerFilepath = mock(Path.class);
        when(innerFilepath.toString()).thenReturn("testfile");
        when(outerFilepath.getFileName()).thenReturn(innerFilepath);
        pathsList.add(outerFilepath);

        when(fileController.storageService.loadAll()).thenReturn(pathsList.stream());

        // When
        List<String> response = fileController.loadAllUploadedFiles();

        // Then
        assertEquals(1, response.size());
        assertEquals("testfile", response.get(0));
    }
}
