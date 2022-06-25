package com.uvic.venus.collections;

import com.uvic.venus.model.UserInfo;

public class UserInfoCollection {
    
    private Integer enabled;
    private UserInfo userInfo;

    public UserInfoCollection(Integer enabled, UserInfo userInfo) {
        this.enabled = enabled;
        this.userInfo = userInfo;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
