package com.uvic.venus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageExceptionTests {
    
    @Test
    public void testStorageExceptionWithStringMessage() {
        // Given & when
        StorageException exception = new StorageException("test exception");

        // Then
        assertEquals(exception.getMessage(), "test exception");
    }

    @Test
    public void testStorageExceptionWithStringMessageAndCause() {
        // Given & when
        Throwable cause = new Throwable("test cause");
        StorageException exception = new StorageException("test exception", cause);

        // Then
        assertEquals(exception.getMessage(), "test exception");
        assertEquals(exception.getCause(), cause); 
    }
}
