package com.uvic.venus.controller;

import com.uvic.venus.model.ContactUs;
import com.uvic.venus.repository.ContactUsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contactus")
public class ContactUsController {

    @Autowired
    ContactUsDAO contactUsDAO;

    @RequestMapping(value="/fetchcontactus", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllNews() {
        List<ContactUs> contactUsList = contactUsDAO.findAll();
        return ResponseEntity.ok(contactUsList);
    }

    @RequestMapping(value="/addcontactus", method = RequestMethod.POST)
    public ResponseEntity<String> addContactUs(@RequestBody Map<String, Object> contactUsAddJSON) {
        ContactUs contactUsAdd = new ContactUs();
        contactUsAdd.setUserName(contactUsAddJSON.get("user_name").toString());
        contactUsAdd.setEmail(contactUsAddJSON.get("email").toString());
        contactUsAdd.setMessage(contactUsAddJSON.get("message").toString());

        contactUsDAO.save(contactUsAdd);
        return ResponseEntity.ok("Successfully added contact-us message");
    }
    
}
