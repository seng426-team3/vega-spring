package com.uvic.venus.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.uvic.venus.repository.SecretDAO;
import com.uvic.venus.auth.JwtUtil;
import com.uvic.venus.model.SecretEntry;

public class VaultControllerTests {
    private VaultController vaultController;
    private SecretEntry testSecret;
    private MultipartFile testFile;
    private List<SecretEntry> testEntries = new ArrayList<>();

    private java.util.Date utilDate = new java.util.Date();
    private java.sql.Date creationDate = new java.sql.Date(utilDate.getTime());

    @BeforeEach 
    public void setup() {
        testEntries.clear();

        // Set up mock classes
        testFile = Mockito.mock(MultipartFile.class);
        vaultController = new VaultController();
        vaultController.secretDAO = Mockito.mock(SecretDAO.class);
        vaultController.jwtUtil = Mockito.mock(JwtUtil.class);

        // Set mock file name
        Mockito.when(testFile.getOriginalFilename()).thenReturn("test.txt");

        // Set up mock database as a List
        testSecret = new SecretEntry("testID", "testuser@venus.com", "Test Secret", ".txt", creationDate, null);

        testEntries.add(testSecret);
    }

    @Test
    public void testFetchSecrets() {
        ResponseEntity<?> result;

        // Test data
        String username = "testuser@venus.com";

        // Mock methods
        Mockito.when(vaultController.jwtUtil.extractUsername("token")).thenReturn(username);
        Mockito.when(vaultController.secretDAO.findByUsername(username)).thenReturn(testEntries);
        
        result = vaultController.fetchSecrets("testingtoken");

        System.out.println(result.getBody().toString());

        // Check that the data base is pulled properly
        Assertions.assertEquals(testEntries, result.getBody());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSecretCreation() throws Exception {
        ResponseEntity<?> result;
        List<SecretEntry> resultList;

        // Test data
        String username = "testuser@venus.com";
        String secretName = "Great Secret";

        // Mock methods
        Mockito.when(vaultController.jwtUtil.extractUsername("token")).thenReturn(username);
        Mockito.when(vaultController.secretDAO.findByUsername(username)).thenReturn(testEntries);
        Mockito.when(vaultController.secretDAO.save(Mockito.any(SecretEntry.class))).thenAnswer(new Answer<SecretEntry>() {
            @Override
            public SecretEntry answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                testEntries.add((SecretEntry) args[0]);
                return(SecretEntry) args[0];
            }
        });

        vaultController.createSecret("testingtoken", secretName, testFile);

        result = vaultController.fetchSecrets("testingtoken");

        resultList = (List<SecretEntry>) result.getBody();

        // Assert that new entry was added to mock database
        Assertions.assertEquals(2, resultList.size());
        Assertions.assertEquals(secretName, resultList.get(1).getSecretName());
        Assertions.assertEquals(username, resultList.get(1).getUsername());
    }
}
