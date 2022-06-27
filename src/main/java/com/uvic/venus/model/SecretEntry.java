package com.uvic.venus.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="secrets")
public class SecretEntry {
    @Id
    private int secretid;
    private String username;
    private String secretname;
    private Date creationdate;
    private byte[] secretdata;


    public SecretEntry(int secretID, String userName, String secretName, Date creationDate, byte[] secretData) {
        this.secretid = secretID;
        this.username = userName;
        this.secretname = secretName;
        this.creationdate = creationDate;
        this.secretdata = secretData;
    }

    public SecretEntry() {

    }

    // Only get method for ID as it is a key
    public int getSecretID() {
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

    public byte[] getSecretData() {
        return secretdata;
    }

    public void setSecretData(byte[] secretData) {
        this.secretdata = secretData;
    }

    @Override
    public String toString() {
        return "SecretEntry{" +
                "secretID='" + secretid + '\'' +
                ", username='" + username + '\'' +
                ", secretName='" + secretname + '\'' +
                ", creationDate='" + creationdate + '\'' +
                ", bytes of data='" + secretdata.length + '\'' +
                '}';
    }
}
