package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;

public class SecretEntryTests {

    @Test
    public void testSecretEntryWithApplicationLogic() {
        // Given & When
        SecretEntry secretEntry = new SecretEntry(
            "testuser",
            "secretname",
            "text",
            "body".getBytes()
        );

        // Then
        assertEquals("testuser", secretEntry.getUsername());
        assertEquals("secretname", secretEntry.getSecretName());
        assertEquals("text", secretEntry.getFileType());
        assertArrayEquals("body".getBytes(), secretEntry.getSecretData());
    }

    @Test
    public void testSecretEntryWithDatabaseFetching() {
        Date date = new Date(12312012);

        // Given & When
        SecretEntry secretEntry = new SecretEntry(
            "secretID",
            "testuser",
            "secretname",
            "text",
            date,
            "body".getBytes()
        );

        // Then
        assertEquals("secretID", secretEntry.getSecretID());
        assertEquals("testuser", secretEntry.getUsername());
        assertEquals("secretname", secretEntry.getSecretName());
        assertEquals("text", secretEntry.getFileType());
        assertEquals(date, secretEntry.getCreatDate());
        assertArrayEquals("body".getBytes(), secretEntry.getSecretData());        
    }
}
