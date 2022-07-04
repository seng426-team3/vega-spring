package com.uvic.venus.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.uvic.venus.model.UserInfo;

public class UserInfoCollectionTests {

    @Mock private UserInfo userInfo;

    @BeforeEach
    public void setup() {
        userInfo = mock(UserInfo.class);
    }
    
    @Test
    public void testUserInfoCollectionWithAllValidParameters() {
        // Given & When
        UserInfoCollection userInfoCollection = new UserInfoCollection(1, "ROLE_ADMIN", userInfo);

        // Then
        assertEquals(1, userInfoCollection.getEnabled());
        assertEquals("ROLE_ADMIN", userInfoCollection.getRole());
        assertEquals(userInfo, userInfoCollection.getUserInfo());
    }

    @Test
    public void testUserInfoCollectionWithEnabledAndUserInfo() {
        // Given & When
        UserInfoCollection userInfoCollection = new UserInfoCollection(1, userInfo);

        // Then
        assertEquals(1, userInfoCollection.getEnabled());
        assertEquals(null, userInfoCollection.getRole());
        assertEquals(userInfo, userInfoCollection.getUserInfo());
    }

    @Test
    public void testUserInfoCollectionWithAllParametersSetEnabled() {
        // Given
        UserInfoCollection userInfoCollection = new UserInfoCollection(1, "ROLE_ADMIN", userInfo);

        // When
        userInfoCollection.setEnabled(0);

        // Then
        assertEquals(0, userInfoCollection.getEnabled());
        assertEquals("ROLE_ADMIN", userInfoCollection.getRole());
        assertEquals(userInfo, userInfoCollection.getUserInfo());
    }

    @Test
    public void testUserInfoCollectionWithAllParametersSetRole() {
        // Given
        UserInfoCollection userInfoCollection = new UserInfoCollection(1, "ROLE_ADMIN", userInfo);

        // When
        userInfoCollection.setRole("ROLE_STAFF");

        // Then
        assertEquals(1, userInfoCollection.getEnabled());
        assertEquals("ROLE_STAFF", userInfoCollection.getRole());
        assertEquals(userInfo, userInfoCollection.getUserInfo());
    }

    @Test
    public void testUserInfoCollectionWithAllParametersSetUserInfo() {
        // Given
        UserInfo userInfoNew = mock(UserInfo.class);
        UserInfoCollection userInfoCollection = new UserInfoCollection(1, "ROLE_ADMIN", userInfo);

        // When
        userInfoCollection.setUserInfo(userInfoNew);

        // Then
        assertEquals(1, userInfoCollection.getEnabled());
        assertEquals("ROLE_ADMIN", userInfoCollection.getRole());
        assertEquals(userInfoNew, userInfoCollection.getUserInfo());
    }
}
