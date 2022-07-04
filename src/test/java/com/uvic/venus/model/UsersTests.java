package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsersTests {
    
    @Test
    public void testUsersWithValidParameters() {
        // Given & When
        Users users = new Users("username", "password", 1);

        // Then
        assertEquals("username", users.getUsername());
        assertEquals("password", users.getPassword());
        assertEquals(1, users.getEnabled());
    }

    @Test
    public void testUsersWithNoParameters() {
        // Given & When
        Users users = new Users();

        // Then
        assertEquals(null, users.getUsername());
        assertEquals(null, users.getPassword());
        assertEquals(null, users.getEnabled());
    }

    @Test
    public void testUsersWithNoParametersAndSetValues() {
        // Given
        Users users = new Users();

        // When
        users.setUsername("username");
        users.setPassword("password");
        users.setEnabled(1);

        // Then
        assertEquals("username", users.getUsername());
        assertEquals("password", users.getPassword());
        assertEquals(1, users.getEnabled());
    }

    @Test
    public void testUsersWithValidParametersAndSetValues() {
        // Given
        Users users = new Users("username", "password", 1);

        // When
        users.setUsername("username2");
        users.setPassword("password2");
        users.setEnabled(0);

        // Then
        assertEquals("username2", users.getUsername());
        assertEquals("password2", users.getPassword());
        assertEquals(0, users.getEnabled());
    }
}
