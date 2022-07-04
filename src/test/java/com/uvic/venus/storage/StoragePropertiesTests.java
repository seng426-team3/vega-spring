package com.uvic.venus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StoragePropertiesTests {

    @Test
    public void testStoragePropertiesWithNonNullString() {
        // Given
        StorageProperties storageProperties = new StorageProperties();
        
        // When
        storageProperties.setLocation("testlocation");

        // Then
        String actualLocation = storageProperties.getLocation();
        assertEquals("testlocation", actualLocation);
    }

    @Test
    public void testStoragePropertiesWithNullString() {
        // Given
        StorageProperties storageProperties = new StorageProperties();
        
        // When
        storageProperties.setLocation(null);

        // Then
        String actualLocation = storageProperties.getLocation();
        assertEquals(null, actualLocation);
    }
}
