package com.uvic.venus.controller;

import com.uvic.venus.collections.UserInfoCollection;
import com.uvic.venus.model.SecretEntry;
import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.Users;
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
    SecretDAO secretDAO;

    @Autowired
    DataSource dataSource;

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/fetchusers", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllUsers(){
        // Return all userinfo attributes
        List<UserInfo> userInfoList = userInfoDAO.findAll();
        
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

        return ResponseEntity.ok(userInfoCollection);
    }

    @RequestMapping(value ="/enableuser", method = RequestMethod.GET)
    public ResponseEntity<?> enableUserAccount(@RequestParam String username, @RequestParam boolean enable){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = manager.loadUserByUsername(username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(userDetails.getAuthorities());
        builder.disabled(!enable);

        manager.updateUser(builder.build());
        return ResponseEntity.ok("User enabled successfully");
    }

    @RequestMapping(value ="/disableuser", method = RequestMethod.GET)
    public ResponseEntity<?> disableUserAccount(@RequestParam String username, @RequestParam boolean disable){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = manager.loadUserByUsername(username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(userDetails.getAuthorities());
        builder.disabled(disable);

        manager.updateUser(builder.build());
        return ResponseEntity.ok("User disabled successfully");
    }

    @RequestMapping(value ="/changerole", method = RequestMethod.GET)
    public ResponseEntity<?> changeRole(@RequestParam String username, @RequestParam String role){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = manager.loadUserByUsername(username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(authorities);
        builder.disabled(userDetails.isEnabled());

        manager.updateUser(builder.build());
        return ResponseEntity.ok("User Updated Successfully");
    }

    @PostMapping(value = "/handlefileupload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        storageService.store(file);
        return ResponseEntity.ok("File uploaded Successfully");
    }

    @RequestMapping(value="/fetchallsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllSecrets() throws Exception{
        List<SecretEntry> secretEntryList = secretDAO.findAll();

        return ResponseEntity.ok(secretEntryList);
    }

}
