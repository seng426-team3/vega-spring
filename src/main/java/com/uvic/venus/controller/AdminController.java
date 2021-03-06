package com.uvic.venus.controller;

import com.uvic.venus.collections.UserInfoCollection;
import com.uvic.venus.model.Authorities;
import com.uvic.venus.model.SecretEntry;
import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.Users;
import com.uvic.venus.repository.AuthoritiesDAO;
import com.uvic.venus.repository.SecretDAO;
import com.uvic.venus.repository.UserInfoDAO;
import com.uvic.venus.repository.UsersDAO;
import com.uvic.venus.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserInfoDAO userInfoDAO;

    @Autowired
    UsersDAO usersDAO;

    @Autowired
    AuthoritiesDAO authoritiesDAO;
  
    @Autowired
    SecretDAO secretDAO;

    @Autowired
    DataSource dataSource;

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/fetchusers", method = RequestMethod.GET)
    public ResponseEntity<List<UserInfoCollection>> fetchAllUsers(){
        // Return all userinfo attributes
        List<UserInfo> userInfoList = userInfoDAO.findAll();
        List<Authorities> authoritiesList = authoritiesDAO.findAll();
        
        // We return a collection of each user info and whether they
        // are enabled or not.
        List<UserInfoCollection> userInfoCollection = new ArrayList<>();
        List<Users> usersList = usersDAO.findAll();
        usersList.stream().forEach((user) -> {
            userInfoList.stream().forEach((userinfo) -> {
                if (user.getUsername().equals(userinfo.getUsername())) {
                    userInfoCollection.add(new UserInfoCollection(user.getEnabled(), userinfo));
                }
            });
        });

        // We add the user's role to the list as well.
        userInfoCollection.stream().forEach((userInfo) -> {
            authoritiesList.stream().forEach((authority) -> {
                if (authority.getUsername().equals(userInfo.getUserInfo().getUsername())) {
                    // The authority entry is equivalent to the role semantics on vega-web
                    userInfo.setRole(authority.getAuthority());
                }
            });
        });

        return ResponseEntity.ok(userInfoCollection);
    }

    @RequestMapping(value ="/enableuser", method = RequestMethod.GET)
    public ResponseEntity<String> enableUserAccount(@RequestParam String username, @RequestParam boolean enable){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = this.loadUserByUsername(manager, username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(userDetails.getAuthorities());
        builder.disabled(!enable);

        this.updateUser(manager, builder.build());
        return ResponseEntity.ok("User enabled successfully");
    }

    @RequestMapping(value ="/disableuser", method = RequestMethod.GET)
    public ResponseEntity<String> disableUserAccount(@RequestParam String username, @RequestParam boolean disable){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = this.loadUserByUsername(manager, username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(userDetails.getAuthorities());
        builder.disabled(disable);

        this.updateUser(manager, builder.build());
        return ResponseEntity.ok("User disabled successfully");
    }

    @RequestMapping(value ="/changerole", method = RequestMethod.GET)
    public ResponseEntity<String> changeRole(@RequestParam String username, @RequestParam String role){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = this.loadUserByUsername(manager, username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(authorities);
        builder.disabled(userDetails.isEnabled());

        this.updateUser(manager, builder.build());
        return ResponseEntity.ok("User Updated Successfully");
    }

    @PostMapping(value = "/handlefileupload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file){
        storageService.store(file);
        return ResponseEntity.ok("File uploaded Successfully");
    }

    @RequestMapping(value="/fetchallsecrets", method = RequestMethod.GET)
    public ResponseEntity<List<SecretEntry>> fetchAllSecrets(){
        return ResponseEntity.ok(secretDAO.findAll());
    }

    public UserDetails loadUserByUsername(JdbcUserDetailsManager manager, String username) {
        return manager.loadUserByUsername(username);
    }

    public boolean updateUser(JdbcUserDetailsManager manager, UserDetails userDetails) {
        manager.updateUser(userDetails);
        return true;
    }
}
