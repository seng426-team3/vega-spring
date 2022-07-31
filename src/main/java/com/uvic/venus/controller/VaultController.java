package com.uvic.venus.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<?> fetchSecrets(@RequestHeader (name="authorization") String jwt){
        // Remove the "Bearer " prefix off the JWT so that jwtUtil accepts it
        String username = jwtUtil.extractUsername(jwt.substring(7));

        List<SecretEntry> userSecrets= secretDAO.findByUsername(username);
        
        return ResponseEntity.ok(userSecrets);
    }

    @RequestMapping(value="/createsecret", method = RequestMethod.POST)
    public ResponseEntity<?> createSecret(@RequestHeader (name="authorization") String jwt, @RequestParam("secretname") String secretname, @RequestPart MultipartFile file) throws Exception{
        // Remove the "Bearer " prefix off the JWT so that jwtUtil accepts it
        String username;
        String fileName;
        String fileEnd;
        
        try {
            username = jwtUtil.extractUsername(jwt.substring(7));
            fileName = file.getOriginalFilename();
            fileEnd = fileName.substring(fileName.indexOf("."));
        } catch(NullPointerException e) {
            return ResponseEntity.ok("Some provided parameters are null.");
        }
        

        byte[] secretData = file.getBytes();

        SecretEntry newSecretEntry = new SecretEntry(username, secretname, fileEnd, secretData);

        secretDAO.save(newSecretEntry);

        return ResponseEntity.ok("Successfully created a secret");
    }

    @RequestMapping(value="/readsecret", method = RequestMethod.POST)
    public ResponseEntity<?> readSecret(@RequestParam String secretid){        
        SecretEntry secret = secretDAO.getById(secretid);

        byte[] file = secret.getSecretData();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + secret.getSecretName() + secret.getFileType() + "\"").body(file);
    }

    @RequestMapping(value="/secretupdate", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestParam String secretid, @RequestParam(required=false) String secretname, @RequestPart(required=false) MultipartFile file) throws Exception {
        SecretEntry secret = secretDAO.getById(secretid);

        String response = "No updates made to secret";

        if (secretname != null && secretname.equals(secret.getSecretName())) {
            secret.setSecretName(secretname);

            response = "Sucessfully updated secret";
        }

        if (file != null) {
            byte[] secretData =  file.getBytes();

            if (secretData != secret.getSecretData()) {
                String fileName;
                String fileEnd;
                
                try {
                    fileName = file.getOriginalFilename();
                    fileEnd = fileName.substring(fileName.indexOf("."));
                } catch (NullPointerException e) {
                    return ResponseEntity.ok("NullPointerException, file to udpate is null");
                }

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
    public ResponseEntity<?> deleteSecret(@RequestParam String secretid){
        secretDAO.deleteById(secretid);

        return ResponseEntity.ok("Successfully deleted secret");
    }
    
    @RequestMapping(value="/sharesecret", method = RequestMethod.POST)
    public ResponseEntity<?> shareSecret(@RequestParam String secretid, @RequestParam String targetuser){        
        SecretEntry secret = secretDAO.getById(secretid);

        SecretEntry newSecretEntry = new SecretEntry(targetuser, secret.getSecretName(), secret.getFileType(), secret.getSecretData());

        secretDAO.save(newSecretEntry);

        return ResponseEntity.ok("Successully shared with user");
    }
}
