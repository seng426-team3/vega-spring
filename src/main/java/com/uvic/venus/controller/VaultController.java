package com.uvic.venus.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.uvic.venus.auth.JwtUtil;
import com.uvic.venus.model.SecretEntry;
import com.uvic.venus.repository.SecretDAO;

@RestController
@RequestMapping("/vault")
public class VaultController {
    @Autowired
    SecretDAO secretDAO;

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value="/fetchsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> fetchSecrets(@RequestParam String token){
        String username = jwtUtil.extractUsername(token);

        List<SecretEntry> userSecrets= secretDAO.findByUsername(username);
        
        return ResponseEntity.ok(userSecrets);
    }

    @RequestMapping(value="/createsecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestParam String secretName, String token){
        String username = jwtUtil.extractUsername(token);

        SecretEntry newSecretEntry = new SecretEntry(username, secretName);

        secretDAO.save(newSecretEntry);

        return ResponseEntity.ok("Successfully created a secret");
    }

    @RequestMapping(value="/readsecret", method = RequestMethod.GET)
    public ResponseEntity<?> readSecret(@RequestParam String secretID){
        // TO DO: Implement user secret reading
        return ResponseEntity.ok("Function not implemented yet");
    }

    @RequestMapping(value="/upatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestBody SecretEntry secret){
        // TO DO: Implement user secret updating
        return ResponseEntity.ok("Function not implemented yet");
    }

    @RequestMapping(value="/deletesecret", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSecret(@RequestParam String secretID){
        secretDAO.deleteById(secretID);

        return ResponseEntity.ok("Successfully deleted secret");
    }
}
