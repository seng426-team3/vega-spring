package com.uvic.venus.model;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="secrets")
public class SecretEntry {

    @Id
    private String secretid;
    private String username;
    private String secretname;
    private Date creationdate;
    private FileInputStream secretdata;

    // Constructor to be used by application logic
    public SecretEntry(String userName, String secretName, FileInputStream secretData) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date creationDate = new java.sql.Date(utilDate.getTime());

        this.secretid = UUID.randomUUID().toString();
        this.username = userName;
        this.secretname = secretName;
        this.creationdate = creationDate;
        this.secretdata = secretData;
    }

    // Constructor used by database fetching
    public SecretEntry(String secretID, String userName, String secretName, Date creationDate, FileInputStream secretData) {
        this.secretid = secretID;
        this.username = userName;
        this.secretname = secretName;
        this.creationdate = creationDate;
        this.secretdata = secretData;
    }

    // Required default constructor
    public SecretEntry() {

    }

    // Only get method for ID as it should never be updated
    public String getSecretID() {
        return secretid;
    }

    public String getUsername() { 
        return username; 
    }

    public void setUsername(String username) { 
        this.username = username;  
    }

    public String getSecretName() {
        return secretname;
    }

    public void setSecretName(String secretName) {
        this.secretname = secretName;
    }

    // Only get method as date should never be updated after creation
    public Date getCreatDate() {
        return creationdate;
    }

    public FileInputStream getSecretData() {
        return secretdata;
    }

    public void setSecretData(FileInputStream secretData) {
        this.secretdata = secretData;
    }

    @Override
    public String toString() {
        return "SecretEntry{" +
                "secretid='" + secretid + '\'' +
                ", username='" + username + '\'' +
                ", secretname='" + secretname + '\'' +
                ", creationdate='" + creationdate + '\'' +
                '}';
    }
}
