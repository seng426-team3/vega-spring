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

    @RequestMapping(value="/fetchsecrets", method = RequestMethod.POST)
    public ResponseEntity<?> fetchSecrets(@RequestPart String token){
        String username = jwtUtil.extractUsername(token);

        List<SecretEntry> userSecrets= secretDAO.findByUsername(username);
        
        return ResponseEntity.ok(userSecrets);
    }

    @RequestMapping(value="/createsecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestPart String secretname, @RequestPart String token, @RequestPart MultipartFile file) throws Exception{
        String username = jwtUtil.extractUsername(token);
        String fileName = file.getOriginalFilename();
        String fileEnd = fileName.substring(fileName.indexOf("."));
        byte[] secretData = file.getBytes();

        SecretEntry newSecretEntry = new SecretEntry(username, secretname, fileEnd, secretData);

        secretDAO.save(newSecretEntry);

        return ResponseEntity.ok("Successfully created a secret");
    }

    @RequestMapping(value="/readsecret", method = RequestMethod.POST)
    public ResponseEntity<?> readSecret(@RequestPart String secretid){        
        SecretEntry secret = secretDAO.getById(secretid);

        byte[] file = secret.getSecretData();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + secret.getSecretName() + secret.getFileType() + "\"").body(file);
    }

    @RequestMapping(value="/upatesecret", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestPart String secretid, @RequestPart String secretname, @RequestPart MultipartFile file) throws Exception {
        SecretEntry secret = secretDAO.getById(secretid);

        String response = "No updates made to secret";

        if (secretname != null && secretname != secret.getSecretName()) {
            secret.setSecretName(secretname);

            response = "Sucessfully updated secret";
        }

        if (file != null) {
            byte[] secretData =  file.getBytes();

            if (secretData != secret.getSecretData()) {
                String fileName = file.getOriginalFilename();
                String fileEnd = fileName.substring(fileName.indexOf("."));

                secret.setFileType(fileEnd);
                secret.setSecretData(secretData);

                response = "Successfully updated secret";
            }
        }

        secretDAO.deleteById(secretid);
        secretDAO.save(secret);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/deletesecret", method = RequestMethod.POST)
    public ResponseEntity<?> deleteSecret(@RequestPart String secretid){
        secretDAO.deleteById(secretid);

        return ResponseEntity.ok("Successfully deleted secret");
    }
}
