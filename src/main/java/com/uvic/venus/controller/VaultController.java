package com.uvic.venus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.uvic.venus.model.SecretEntry;
import com.uvic.venus.repository.SecretDAO;

@RestController
@RequestMapping("/vault")
public class VaultController {
    @Autowired
    SecretDAO secretDAO;

    @RequestMapping(value="/userfetchsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> fetchSecrets() throws Exception{
        // TO DO: Implement user secret fetching

        return ResponseEntity.ok("Function not implemented");
    }

    @RequestMapping(value="/fetchallsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllSecrets() throws Exception{
        List<SecretEntry> secretEntryList = secretDAO.findAll();

        return ResponseEntity.ok(secretEntryList);
    }

    @RequestMapping(value="/usercreatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestBody String secretName, byte[] secretData) throws Exception{
        // TO DO: Implement user secret creation
        return ResponseEntity.ok("Function not implemented");
    }

    @RequestMapping(value="/userreadsecret", method = RequestMethod.GET)
    public ResponseEntity<?> readSecret(@RequestParam int secretID) throws Exception{
        // TO DO: Implement user secret reading
        return ResponseEntity.ok("Function not implemented");
    }

    @RequestMapping(value="/userupatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestBody SecretEntry secret) throws Exception{
        // TO DO: Implement user secret updating
        return ResponseEntity.ok("Function not implemented");
    }

    @RequestMapping(value="/userdeletesecret", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSecret(@RequestParam int secretID) throws Exception{
        // TO DO: Implement user secret deletion
        return ResponseEntity.ok("Function not implemented");
    }
}
