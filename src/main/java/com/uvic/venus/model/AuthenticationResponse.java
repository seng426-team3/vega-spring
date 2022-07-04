package com.uvic.venus.model;

public class AuthenticationResponse {

    private final String jwt;
    Object[] authorities;

    public AuthenticationResponse(String jwt, Object[] authorities) {
        this.jwt = jwt;
        this.authorities = authorities;
    }

    public String getJwt() {
        return jwt;
    }

    public Object[] getAuthorities() {
        return authorities;
    }

}
