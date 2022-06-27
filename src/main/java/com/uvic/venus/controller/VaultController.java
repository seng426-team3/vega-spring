package com.uvic.venus.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> fetchSecrets(@RequestPart String token){
        String username = jwtUtil.extractUsername(token);

        List<SecretEntry> userSecrets= secretDAO.findByUsername(username);
        
        return ResponseEntity.ok(userSecrets);
    }

    @RequestMapping(value="/createsecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestPart String secretname, @RequestPart String token, @RequestPart MultipartFile file) throws Exception{
        String username = jwtUtil.extractUsername(token);

        String fileName = file.getOriginalFilename();

        byte[] secretData = file.getBytes();

        SecretEntry newSecretEntry = new SecretEntry(username, secretname, fileName, secretData);

        secretDAO.save(newSecretEntry);

        return ResponseEntity.ok("Successfully created a secret");
    }

    @RequestMapping(value="/readsecret", method = RequestMethod.GET)
    public ResponseEntity<?> readSecret(@RequestPart String secretid){        
        SecretEntry secret = secretDAO.getById(secretid);

        byte[] file = secret.getSecretData();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + secret.getFileName() + "\"").body(file);
    }

    @RequestMapping(value="/upatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestPart String secretID, @RequestPart String secretName, @RequestPart MultipartFile file) throws Exception {
        SecretEntry secret = secretDAO.findById(secretID).get();

        String response = "No updates made to secret";

        if (secretName != "" && secretName != secret.getSecretName()) {
            // TO DO: Update secret name functionality
            response = "Sucessfully updated secret";
        }

        if (file != null) {
            byte[] secretData =  file.getBytes();

            if (secretData != secret.getSecretData()) {
                // TO DO: Update secret data functionality
                response = "Successfully updated secret";
            }
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/deletesecret", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSecret(@RequestPart String secretid){
        secretDAO.deleteById(secretid);

        return ResponseEntity.ok("Successfully deleted secret");
    }
}
