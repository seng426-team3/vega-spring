package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserInfoTests {
    
    @Test
    public void testUserInfoWithValidParameters() {
        // Given & When
        UserInfo userInfo = new UserInfo("username", "firstname", "lastname");

        // Then
        assertEquals("username", userInfo.getUsername());
        assertEquals("firstname", userInfo.getFirstName());
        assertEquals("lastname", userInfo.getLastName());
    }

    @Test
    public void testUserInfoWithNoParameters() {
        // Given & When
        UserInfo userInfo = new UserInfo();

        // Then
        assertEquals(null, userInfo.getUsername());
        assertEquals(null, userInfo.getFirstName());
        assertEquals(null, userInfo.getLastName());        
    }

    @Test
    public void testUserInfoWithNoParametersAndSetParameters() {
        // Given
        UserInfo userInfo = new UserInfo();

        // When
        userInfo.setFirstName("firstname");
        userInfo.setLastName("lastname");
        userInfo.setUsername("username");

        // Then
        assertEquals("username", userInfo.getUsername());
        assertEquals("firstname", userInfo.getFirstName());
        assertEquals("lastname", userInfo.getLastName());        
    }

    @Test
    public void testUserInfoWithValidParametersAndSetParameters() {
        // Given
        UserInfo userInfo = new UserInfo("username", "firstname", "lastname");

        // When
        userInfo.setFirstName("firstname2");
        userInfo.setLastName("lastname2");
        userInfo.setUsername("username2");

        // Then
        assertEquals("username2", userInfo.getUsername());
        assertEquals("firstname2", userInfo.getFirstName());
        assertEquals("lastname2", userInfo.getLastName());        
    }
}
