package com.uvic.venus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class StorageServiceImplTests {

    StorageServiceImpl storageServiceImpl;
    StorageServiceImpl storageServiceImplSpy;
    StorageProperties storageProperties;

    @BeforeEach
    public void setup() {
        storageProperties = new StorageProperties();
        storageServiceImpl = new StorageServiceImpl(storageProperties);
        storageServiceImplSpy = spy(storageServiceImpl);
    }

    @Test
    public void testStoreWithValidMultipartFileButWrongPath() {
        // Given
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "Hello!".getBytes());
    
        // When
        Throwable exception = assertThrows(StorageException.class, () -> storageServiceImplSpy.store(multipartFile));
        
        // Then
        assertEquals("Cannot store file outside current directory.", exception.getMessage());
    }

    @Test
    public void testStoreWithEmptyMultipartFile() {
        // Given
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "test.txt", "text", "".getBytes());
    
        // When
        Throwable exception = assertThrows(StorageException.class, () -> storageServiceImplSpy.store(multipartFile));
        
        // Then
        assertEquals("Failed to store empty file.", exception.getMessage());
    }

    @Test
    public void testStoreWithValidMultipartFile() throws Exception {
        // Given
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "test.txt", "text", "Hello!".getBytes());
        doReturn(1L).when(storageServiceImplSpy).copyInputStreamIntoDestinationFile(any(), any(), any());
        
        // When & Then
        storageServiceImplSpy.store(multipartFile);
        // If no exception thrown, then file stored successfully.
    }

    @Test
    public void testLoadAllWithFilesExisting() throws Exception {
        // Given 
        List<Path> returnedPathList = new ArrayList<>();
        returnedPathList.add(mock(Path.class));
        Stream<Path> returnedPathStreams = returnedPathList.stream();
        
        doReturn(true).when(storageServiceImplSpy).createNewDirectoryIfNotExists();
        doReturn(returnedPathStreams).when(storageServiceImplSpy).returnAllFiles();

        // When & Then
        storageServiceImpl.loadAll();
    }

    @Test
    public void testLoadAllWithIOExceptionThrownAtDirectoryCall() throws Exception {
        // Given
        doThrow(new IOException("exception")).when(storageServiceImplSpy).createNewDirectoryIfNotExists();
        
        // When & Then
        Exception exception = assertThrows(StorageException.class, () -> {
            storageServiceImplSpy.loadAll();
        });

        assertEquals("Failed to read stored files", exception.getMessage());
    }

    @Test
    public void testLoadAllWithIOExceptionThrownAtReturnAllFilesCall() throws Exception {
        // Given         
        doReturn(true).when(storageServiceImplSpy).createNewDirectoryIfNotExists();
        doThrow(new IOException("exception")).when(storageServiceImplSpy).returnAllFiles();

        // When
        Exception exception = assertThrows(StorageException.class, () -> {
            storageServiceImplSpy.loadAll();
        });
        
        // Then
        assertEquals("Failed to read stored files", exception.getMessage());
    }
}
