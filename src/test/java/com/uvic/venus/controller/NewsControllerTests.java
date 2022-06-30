package com.uvic.venus.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.uvic.venus.model.News;
import com.uvic.venus.repository.NewsDAO;

public class NewsControllerTests {


    private NewsController newsController;

    @BeforeEach
    public void setup() {
        newsController = new NewsController();
        newsController.newsDAO = mock(NewsDAO.class);
    }

    @Test
    public void testFetchNewsWithEmptyNews() {
        // Given
        List<News> newsList = new ArrayList<>();
        newsList.add(new News());
        when(newsController.newsDAO.findAll()).thenReturn(newsList);

        // When
        ResponseEntity<List<News>> response = newsController.fetchAllNews();

        // Then
        assertEquals(newsList, response.getBody());
    }

    @Test
    public void testFetchNewsWithNonEmptyNews() {
        // Given
        List<News> newsList = new ArrayList<>();
        newsList.add(new News(1, "title", "bodytext", "date", 12321, "author"));
        when(newsController.newsDAO.findAll()).thenReturn(newsList);

        // When
        ResponseEntity<List<News>> response = newsController.fetchAllNews();

        // Then
        assertEquals(newsList, response.getBody());
    }

    @Test
    public void testFetchNewsWithNoNews() {
        // Given
        List<News> newsList = new ArrayList<>();
        when(newsController.newsDAO.findAll()).thenReturn(newsList);

        // When
        ResponseEntity<List<News>> response = newsController.fetchAllNews();

        // Then
        assertEquals(newsList, response.getBody());
    }

    @Test
    public void testAddNewsWithNews() {
        // Given
        Map<String, Object> newsToAdd = new HashMap<String, Object>();
        newsToAdd.put("timepublished", 2131212);
        newsToAdd.put("title", "testtitle");
        newsToAdd.put("bodytext", "bodytext2");
        newsToAdd.put("newsdate", "testdate");
        newsToAdd.put("author", "testauthor");
        when(newsController.newsDAO.save(any())).thenReturn(null);

        // When
        ResponseEntity<String> response = newsController.addNews(newsToAdd);

        // Then
        assertEquals("Successfully added news article", response.getBody());
    }

    @Test
    public void testEditNewsWithNews() {
        // Given
        Map<String, Object> newsToEdit = new HashMap<String, Object>();
        newsToEdit.put("timepublished", 2131212);
        newsToEdit.put("title", "testtitle");
        newsToEdit.put("bodytext", "bodytext2");
        newsToEdit.put("newsdate", "testdate");
        newsToEdit.put("author", "testauthor");
        when(newsController.newsDAO.save(any())).thenReturn(null);

        // When
        ResponseEntity<String> response = newsController.editNews(newsToEdit);

        // Then
        assertEquals("Successfully edited news article", response.getBody());
    }

    @Test
    public void testDeleteNewsWithNewsIdAndNewsArticle() {
        // Given
        Map<String, Object> newsToEdit = new HashMap<String, Object>();
        newsToEdit.put("newsid", 50);
        newsToEdit.put("timepublished", 2131212);
        newsToEdit.put("title", "testtitle");
        newsToEdit.put("bodytext", "bodytext2");
        newsToEdit.put("newsdate", "testdate");
        newsToEdit.put("author", "testauthor");

        // When
        ResponseEntity<String> response = newsController.deleteNews(newsToEdit);

        // Then
        assertEquals("Successfully deleted news article", response.getBody());
    }

    @Test
    public void testDeleteNewsWithNewsIdAndNoNewsArticle() {
        // Given
        Map<String, Object> newsToEdit = new HashMap<String, Object>();
        newsToEdit.put("newsid", 50);

        // When
        ResponseEntity<String> response = newsController.deleteNews(newsToEdit);

        // Then
        assertEquals("Successfully deleted news article", response.getBody());
    }
}
