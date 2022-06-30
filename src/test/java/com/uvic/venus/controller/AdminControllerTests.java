package com.uvic.venus.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.multipart.MultipartFile;

import com.uvic.venus.repository.UsersDAO;
import com.uvic.venus.collections.UserInfoCollection;
import com.uvic.venus.storage.StorageService;
import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.Users;
import com.uvic.venus.model.Authorities;
import com.uvic.venus.model.SecretEntry;
import com.uvic.venus.repository.AuthoritiesDAO;
import com.uvic.venus.repository.SecretDAO;
import com.uvic.venus.repository.UserInfoDAO;

@RunWith(PowerMockRunner.class)
@PrepareForTest(User.UserBuilder.class)
public class AdminControllerTests {

    AdminController adminController;
    @Mock private MultipartFile multipartFile;
    @InjectMocks private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        adminController = spy(AdminController.class);
        adminController.userInfoDAO = mock(UserInfoDAO.class);
        adminController.usersDAO = mock(UsersDAO.class);
        adminController.authoritiesDAO = mock(AuthoritiesDAO.class);
        adminController.secretDAO = mock(SecretDAO.class);
        adminController.dataSource = mock(DataSource.class);
        adminController.storageService = mock(StorageService.class);
    }
    
    @Test
    public void testFetchAllUsersWithMissingUserInTableReturnsApplicableUsers() {
        // Setup user info list
        ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
        userInfoList.add(new UserInfo("user1", "name1", "lastname1"));
        userInfoList.add(new UserInfo("user2", "name2", "lastname2"));

        // Setup authorities
        ArrayList<Authorities> authoritiesList = new ArrayList<Authorities>();
        authoritiesList.add(new Authorities("user1", "ROLE_ADMIN"));
        authoritiesList.add(new Authorities("user2", "ROLE_STAFF"));

        // Setup Users
        ArrayList<Users> usersList = new ArrayList<Users>();
        usersList.add(new Users("user1", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a", 1));
        usersList.add(new Users("user2", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a", 0));
        usersList.add(new Users("user3", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a", 0));

        // Given
        when(adminController.userInfoDAO.findAll()).thenReturn(userInfoList);
        when(adminController.authoritiesDAO.findAll()).thenReturn(authoritiesList);
        when(adminController.usersDAO.findAll()).thenReturn(usersList);

        // When
        ResponseEntity<List<UserInfoCollection>> response = adminController.fetchAllUsers();

        // Then
        ArrayList<UserInfoCollection> expectedResponse = new ArrayList<>();
        expectedResponse.add(new UserInfoCollection(1, "ROLE_ADMIN", new UserInfo("user1", "name1", "lastname1")));
        expectedResponse.add(new UserInfoCollection(0, "ROLE_STAFF", new UserInfo("user2", "name2", "lastname2")));

        // Compare expected response and actual response
        List<UserInfoCollection> userInfoCollections = response.getBody();
        assertEquals(expectedResponse.size(), userInfoCollections.size());
        for (int i=0; i<userInfoCollections.size(); i++) {
            UserInfoCollection actual = userInfoCollections.get(i);
            UserInfoCollection expected = expectedResponse.get(i);
            

            assertEquals(actual.getEnabled(), expected.getEnabled());
            assertEquals(actual.getRole(), expected.getRole());
            assertEquals(actual.getUserInfo().getUsername(), expected.getUserInfo().getUsername());
            assertEquals(actual.getUserInfo().getFirstName(), expected.getUserInfo().getFirstName());
            assertEquals(actual.getUserInfo().getLastName(), expected.getUserInfo().getLastName());
        }
    }

    @Test
    public void testFetchAllUsersWithExistingUsersReturnsAllUsers() {
        // Setup user info list
        ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
        userInfoList.add(new UserInfo("user1", "name1", "lastname1"));
        userInfoList.add(new UserInfo("user2", "name2", "lastname2"));
        userInfoList.add(new UserInfo("user3", "name3", "lastname3"));

        // Setup authorities
        ArrayList<Authorities> authoritiesList = new ArrayList<Authorities>();
        authoritiesList.add(new Authorities("user1", "ROLE_ADMIN"));
        authoritiesList.add(new Authorities("user2", "ROLE_STAFF"));
        authoritiesList.add(new Authorities("user3", "ROLE_USER"));

        // Setup Users
        ArrayList<Users> usersList = new ArrayList<Users>();
        usersList.add(new Users("user1", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a", 1));
        usersList.add(new Users("user2", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a", 0));
        usersList.add(new Users("user3", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a", 0));

        // Given
        when(adminController.userInfoDAO.findAll()).thenReturn(userInfoList);
        when(adminController.authoritiesDAO.findAll()).thenReturn(authoritiesList);
        when(adminController.usersDAO.findAll()).thenReturn(usersList);

        // When
        ResponseEntity<List<UserInfoCollection>> response = adminController.fetchAllUsers();

        // Then
        ArrayList<UserInfoCollection> expectedResponse = new ArrayList<>();
        expectedResponse.add(new UserInfoCollection(1, "ROLE_ADMIN", new UserInfo("user1", "name1", "lastname1")));
        expectedResponse.add(new UserInfoCollection(0, "ROLE_STAFF", new UserInfo("user2", "name2", "lastname2")));
        expectedResponse.add(new UserInfoCollection(0, "ROLE_USER", new UserInfo("user3", "name3", "lastname3")));

        // Compare expected response and actual response
        List<UserInfoCollection> userInfoCollections = response.getBody();
        assertEquals(expectedResponse.size(), userInfoCollections.size());
        for (int i=0; i<userInfoCollections.size(); i++) {
            UserInfoCollection actual = userInfoCollections.get(i);
            UserInfoCollection expected = expectedResponse.get(i);
            

            assertEquals(actual.getEnabled(), expected.getEnabled());
            assertEquals(actual.getRole(), expected.getRole());
            assertEquals(actual.getUserInfo().getUsername(), expected.getUserInfo().getUsername());
            assertEquals(actual.getUserInfo().getFirstName(), expected.getUserInfo().getFirstName());
            assertEquals(actual.getUserInfo().getLastName(), expected.getUserInfo().getLastName());
        }
    }

    @Test
    public void testEnableUserWithValidParameters() throws Exception {
        // Given
        String test_username = "testuser1";
        UserDetails testUserDetails = mock(UserDetails.class);
        JdbcUserDetailsManager manager = mock(JdbcUserDetailsManager.class);
        PowerMockito.whenNew(JdbcUserDetailsManager.class).withArguments(adminController.dataSource).thenReturn(manager);

        // Setup dummy authorities list
        final List<GrantedAuthority> dummyList = new ArrayList<GrantedAuthority>();
        dummyList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Answer<List<GrantedAuthority>> dummyAuthorities = new Answer<List<GrantedAuthority>>() {
            public List<GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
                return dummyList;
            };
        };

        when(testUserDetails.getAuthorities()).thenAnswer(dummyAuthorities);       
        when(testUserDetails.getPassword()).thenReturn("$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a");
        when(testUserDetails.getUsername()).thenReturn("user1");
        doReturn(testUserDetails).when(adminController).loadUserByUsername(any(), any());
        doReturn(true).when(adminController).updateUser(any(), any());

        // When 
        ResponseEntity<String> response = adminController.enableUserAccount(test_username, true);

        // Then
        assertEquals(response.getBody(), "User enabled successfully");
    }

    @Test
    public void testDisableUserWithValidParameters() throws Exception {
        // Given
        String test_username = "testuser2";
        UserDetails testUserDetails = mock(UserDetails.class);
        JdbcUserDetailsManager manager = mock(JdbcUserDetailsManager.class);
        PowerMockito.whenNew(JdbcUserDetailsManager.class).withArguments(adminController.dataSource).thenReturn(manager);

        // Setup dummy authorities list
        final List<GrantedAuthority> dummyList = new ArrayList<GrantedAuthority>();
        dummyList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Answer<List<GrantedAuthority>> dummyAuthorities = new Answer<List<GrantedAuthority>>() {
            public List<GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
                return dummyList;
            };
        };

        when(testUserDetails.getAuthorities()).thenAnswer(dummyAuthorities);       
        when(testUserDetails.getPassword()).thenReturn("$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a");
        when(testUserDetails.getUsername()).thenReturn("user1");
        doReturn(testUserDetails).when(adminController).loadUserByUsername(any(), any());
        doReturn(true).when(adminController).updateUser(any(), any());

        // When
        ResponseEntity<String> response = adminController.disableUserAccount(test_username, true);

        // Then
        assertEquals(response.getBody(), "User disabled successfully");
    }

    @Test
    public void testChangeRoleWithValidParameters() throws Exception {
        // Given
        String test_username = "testuser3";
        UserDetails testUserDetails = mock(UserDetails.class);
        String test_role = "ROLE_STAFF";
        JdbcUserDetailsManager manager = mock(JdbcUserDetailsManager.class);
        PowerMockito.whenNew(JdbcUserDetailsManager.class).withArguments(adminController.dataSource).thenReturn(manager);

        when(testUserDetails.getPassword()).thenReturn("$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a");
        when(testUserDetails.getUsername()).thenReturn("user1");
        when(testUserDetails.isEnabled()).thenReturn(true);
        doReturn(testUserDetails).when(adminController).loadUserByUsername(any(), any());
        doReturn(true).when(adminController).updateUser(any(), any());

        // When
        ResponseEntity<String> response = adminController.changeRole(test_username, test_role);

        // Then
        assertEquals(response.getBody(), "User Updated Successfully");
    }

    @Test
    public void testHandleFileUploadSuccessful() {
        // Given & When
        ResponseEntity<String> response = adminController.handleFileUpload(multipartFile);
        verify(adminController.storageService, times(1)).store(multipartFile);

        // Then
        assertEquals(response.getBody(), "File uploaded Successfully");
    }

    @Test
    public void testFetchAllSecrets() {
        // Given
        List<SecretEntry> testSecretEntrieslist = new ArrayList<>();
        testSecretEntrieslist.add(new SecretEntry("user1", "secret1", "text", "text".getBytes()));
        testSecretEntrieslist.add(new SecretEntry("user2", "secret2", "text", "text".getBytes()));
        when(adminController.secretDAO.findAll()).thenReturn(testSecretEntrieslist);

        // When
        ResponseEntity<List<SecretEntry>> response = adminController.fetchAllSecrets();

        // Then
        assertEquals(testSecretEntrieslist, response.getBody());
    }
}
