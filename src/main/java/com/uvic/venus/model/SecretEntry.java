package com.uvic.venus.model;

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
    private String secretfilename;
    private Date creationdate;
    private byte[] secretdata;

    // Constructor to be used by application logic
    public SecretEntry(String userName, String secretName, String fileName, byte[] secretData) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date creationDate = new java.sql.Date(utilDate.getTime());

        this.secretid = UUID.randomUUID().toString();
        this.username = userName;
        this.secretname = secretName;
        this.secretfilename = fileName;
        this.creationdate = creationDate;
        this.secretdata = secretData;
    }

    // Constructor used by database fetching
    public SecretEntry(String secretID, String userName, String secretName, String fileName, Date creationDate, byte[] secretData) {
        this.secretid = secretID;
        this.username = userName;
        this.secretname = secretName;
        this.secretfilename = fileName;
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

    public String getFileName() {
        return secretfilename;
    }

    public void setFileName(String fileName) {
        this.secretfilename = fileName;
    }

    // Only get method as date should never be updated after creation
    public Date getCreatDate() {
        return creationdate;
    }

    public byte[] getSecretData() {
        return secretdata;
    }

    public void setSecretData(byte[] secretData) {
        this.secretdata = secretData;
    }

    @Override
    public String toString() {
        return "SecretEntry{" +
                "secretid='" + secretid + '\'' +
                ", username='" + username + '\'' +
                ", secretname='" + secretname + '\'' +
                ", secretfilename='" + secretfilename + '\'' +
                ", creationdate='" + creationdate + '\'' +
                '}';
    }
}
