package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthenticationResponseTests {
    
    @Test
    public void testAuthenticationResponseWithValidParameters() {
        // Given
        SimpleGrantedAuthority[] authoritiesList = new SimpleGrantedAuthority[1];
        authoritiesList[0] = new SimpleGrantedAuthority("ROLE_ADMIN");

        // When
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("testjwt", authoritiesList);

        // Then
        assertEquals("testjwt", authenticationResponse.getJwt());
        assertEquals(authoritiesList, authenticationResponse.getAuthorities());
    }
}
