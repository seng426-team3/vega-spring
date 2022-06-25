package com.uvic.venus.controller;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uvic.venus.model.SecretEntry;
import com.uvic.venus.repository.SecretDAO;

@RestController
@RequestMapping("/vault")
public class VaultController {
    @Autowired
    SecretDAO secretDAO;

    @Autowired
    DataSource dataSource;

    @RequestMapping(value="/userfetchsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> fetchSecrets() throws Exception{
    
    }

    @RequestMapping(value="/fetchallsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllSecrets() throws Exception{
    
    }

    @RequestMapping(value="/usercreatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestBody SecretEntry secret) throws Exception{
    
    }

    @RequestMapping(value="/userreadsecret", method = RequestMethod.GET)
    public ResponseEntity<?> readSecret(@RequestParam int secretID) throws Exception{
    
    }

    @RequestMapping(value="/userupatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestBody SecretEntry secret) throws Exception{
    
    }

    @RequestMapping(value="/userdeletesecret", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSecret(@RequestParam int secretID) throws Exception{
    
    }
}
