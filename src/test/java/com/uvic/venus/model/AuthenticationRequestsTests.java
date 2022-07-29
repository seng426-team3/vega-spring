package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationRequestsTests {

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testAuthenticationRequestWithValidParameters() {
        // Given & When
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user1", "pass1");

        // Then
        assertEquals("user1", authenticationRequest.getUsername());
        assertEquals("pass1", authenticationRequest.getPassword());
    }

    @Test
    public void testAuthenticationRequestWithValidParametersSetUsername() {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user1", "pass1");

        // When
        authenticationRequest.setUsername("user2");

        // Then
        assertEquals("user2", authenticationRequest.getUsername());
        assertEquals("pass1", authenticationRequest.getPassword());        
    }

    @Test
    public void testAuthenticationRequestWithValidParametersSetPassword() {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user1", "pass1");

        // When
        authenticationRequest.setPassword("pass2");

        // Then
        assertEquals("user1", authenticationRequest.getUsername());
        assertEquals("pass2", authenticationRequest.getPassword());        
    }
}
