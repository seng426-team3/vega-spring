package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RegisterUserInfoTests {
    
    @Test
    public void testRegisterUserInfoWithValidParameters() {
        // Given & When
        RegisterUserInfo registerUserInfo = new RegisterUserInfo("user1", "firstname", "lastname", "password");

        // Then
        assertEquals("user1", registerUserInfo.getUsername());
        assertEquals("firstname", registerUserInfo.getFirstname());
        assertEquals("lastname", registerUserInfo.getLastname());
        assertEquals("password", registerUserInfo.getPassword());
    }

    @Test
    public void testRegisterUserInfoWithValidParametersSetUsername() {
        // Given
        RegisterUserInfo registerUserInfo = new RegisterUserInfo("user1", "firstname", "lastname", "password");

        // When
        registerUserInfo.setUsername("user2");

        // Then
        assertEquals("user2", registerUserInfo.getUsername());
        assertEquals("firstname", registerUserInfo.getFirstname());
        assertEquals("lastname", registerUserInfo.getLastname());
        assertEquals("password", registerUserInfo.getPassword());        
    }

    @Test
    public void testRegisterUserInfoWithValidParametersSetFirstname() {
        // Given
        RegisterUserInfo registerUserInfo = new RegisterUserInfo("user1", "firstname", "lastname", "password");

        // When
        registerUserInfo.setFirstname("firstname2");

        // Then
        assertEquals("user1", registerUserInfo.getUsername());
        assertEquals("firstname2", registerUserInfo.getFirstname());
        assertEquals("lastname", registerUserInfo.getLastname());
        assertEquals("password", registerUserInfo.getPassword());        
    }

    @Test
    public void testRegisterUserInfoWithValidParametersSetLastname() {
        // Given
        RegisterUserInfo registerUserInfo = new RegisterUserInfo("user1", "firstname", "lastname", "password");

        // When
        registerUserInfo.setLastname("lastname2");

        // Then
        assertEquals("user1", registerUserInfo.getUsername());
        assertEquals("firstname", registerUserInfo.getFirstname());
        assertEquals("lastname2", registerUserInfo.getLastname());
        assertEquals("password", registerUserInfo.getPassword());        
    }

    @Test
    public void testRegisterUserInfoWithValidParametersSetPassword() {
        // Given
        RegisterUserInfo registerUserInfo = new RegisterUserInfo("user1", "firstname", "lastname", "password");

        // When
        registerUserInfo.setPassword("password2");

        // Then
        assertEquals("user1", registerUserInfo.getUsername());
        assertEquals("firstname", registerUserInfo.getFirstname());
        assertEquals("lastname", registerUserInfo.getLastname());
        assertEquals("password2", registerUserInfo.getPassword());        
    }
}
