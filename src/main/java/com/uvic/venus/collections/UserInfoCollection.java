package com.uvic.venus.collections;

import com.uvic.venus.model.UserInfo;

public class UserInfoCollection {
    
    private Integer enabled;
    private String role;
    private UserInfo userInfo;

    public UserInfoCollection(Integer enabled, String role, UserInfo userInfo) {
        this.enabled = enabled;
        this.role = role;
        this.userInfo = userInfo;
    }

    public UserInfoCollection(Integer enabled, UserInfo userInfo) {
        this.enabled = enabled;
        this.userInfo = userInfo;       
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
