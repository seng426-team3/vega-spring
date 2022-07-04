package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AuthoritiesTests {
    
    @Test
    public void testAuthoritiesWithAllValidParameters() {
        // Given & When
        Authorities authorities = new Authorities("user1", "admin");

        // Then
        assertEquals("user1", authorities.getUsername());
        assertEquals("admin", authorities.getAuthority());
    }

    @Test
    public void testAuthoritiesWithNoParameters() {
        // Given & When
        Authorities authorities = new Authorities();

        // Then
        assertEquals(null, authorities.getUsername());
        assertEquals(null, authorities.getAuthority());
    }
}
