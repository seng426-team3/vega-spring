package com.uvic.venus.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
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
    public ResponseEntity<?> fetchSecrets(@RequestParam String token){
        String username = jwtUtil.extractUsername(token);

        List<SecretEntry> userSecrets= secretDAO.findByUsername(username);
        
        return ResponseEntity.ok(userSecrets);
    }

    @RequestMapping(value="/createsecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestParam String secretName, String token, MultipartFile file) throws Exception{
        String username = jwtUtil.extractUsername(token);

        FileInputStream secretData = (FileInputStream) file.getInputStream();

        SecretEntry newSecretEntry = new SecretEntry(username, secretName, secretData);

        secretDAO.save(newSecretEntry);

        return ResponseEntity.ok("Successfully created a secret");
    }

    @RequestMapping(value="/readsecret", method = RequestMethod.GET)
    public ResponseEntity<?> readSecret(@RequestParam String secretID){        
        SecretEntry secret = secretDAO.findById(secretID).get();

        FileInputStream secretData = secret.getSecretData();

        MultipartFile file = (MultipartFile) secretData;

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getName() + "\"").body(file);
    }

    @RequestMapping(value="/upatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestParam String secretID, String secretName, MultipartFile file) throws Exception {
        SecretEntry secret = secretDAO.findById(secretID).get();

        String response = "No updates made to secret";

        if (secretName != "" && secretName != secret.getSecretName()) {
            response = "Sucessfully updated secret";
        }

        if (file != null) {
            FileInputStream secretData = (FileInputStream) file.getInputStream();

            if (secretData != secret.getSecretData()) {
                response = "Successfully updated secret";
            }
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/deletesecret", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSecret(@RequestParam String secretID){
        secretDAO.deleteById(secretID);

        return ResponseEntity.ok("Successfully deleted secret");
    }
}
