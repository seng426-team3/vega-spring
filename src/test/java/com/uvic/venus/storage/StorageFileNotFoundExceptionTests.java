package com.uvic.venus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageFileNotFoundExceptionTests {

    @Test
    public void testStorageFileNotFoundExceptionWithStringMessage() {
        // Given & when
        StorageFileNotFoundException exception = new StorageFileNotFoundException("test exception");

        // Then
        assertEquals(exception.getMessage(), "test exception");
    }

    @Test
    public void testStorageFileNotFoundExceptionWithStringMessageAndCause() {
        // Given & when
        Throwable cause = new Throwable("test cause");
        StorageFileNotFoundException exception = new StorageFileNotFoundException("test exception", cause);

        // Then
        assertEquals(exception.getMessage(), "test exception");
        assertEquals(exception.getCause(), cause); 
    }
}
