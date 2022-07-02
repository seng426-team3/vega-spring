package com.uvic.venus.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import javax.sql.DataSource;

import com.uvic.venus.model.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.uvic.venus.auth.JwtUtil;
import com.uvic.venus.model.AuthenticationRequest;
import com.uvic.venus.model.RegisterUserInfo;
import com.uvic.venus.repository.UserInfoDAO;

import java.util.Arrays;


public class LoginControllerTests {
  @Mock private JwtUtil jwtUtil;
  @Mock private DataSource dataSource;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private UserInfoDAO userInfoDAO;
  @Mock private AuthenticationManager authenticationManager;
  @InjectMocks @Spy LoginController loginController;


  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAuthenticateWithAuthorizedCredentials() throws Exception {
    // Given
    AuthenticationRequest authenticationRequest = new AuthenticationRequest("testname", "pass123");
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(token)).thenReturn(authentication);

    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
    UserDetails userDetails = new User("testname", "pass123", Arrays.asList(authority));
    doReturn(userDetails).when(loginController).loadByUserName(any(JdbcUserDetailsManager.class), anyString());

    // When
    ResponseEntity<?> response = loginController.createAuthenticationToken(authenticationRequest);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void testAuthenticateWithUnauthorizedCredentials() throws Exception {
    // Given
    AuthenticationRequest authenticationRequest = new AuthenticationRequest("unauth", "pass123");
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    when(authenticationManager.authenticate(token)).thenThrow(BadCredentialsException.class);

    // When
    ResponseEntity<?> response = loginController.createAuthenticationToken(authenticationRequest);

    // Then
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("User Not Found", response.getBody());
  }

  @Test
  public void testRegisterUser() throws Exception {
  // Given
    RegisterUserInfo user = new RegisterUserInfo("test", "lasttest", "testname", "pass123");
    when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
    doNothing().when(loginController).createUserData(any(JdbcUserDetailsManager.class), any(User.UserBuilder.class));

    // When
    ResponseEntity<?> response = loginController.registerUser(user);

    // Then
    assertEquals("User Created Successfully", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testDuplicateUserRegisterError() throws Exception {
    // Given
    RegisterUserInfo user = new RegisterUserInfo("test", "lasttest", "testname", "pass123");
    when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
    doNothing().when(loginController).createUserData(any(JdbcUserDetailsManager.class), any(User.UserBuilder.class));
    when(userInfoDAO.save(any(UserInfo.class))).thenThrow(DuplicateKeyException.class);

    // When
    ResponseEntity<?> response = loginController.registerUser(user);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Error: User already registered.", response.getBody());
  }
}
