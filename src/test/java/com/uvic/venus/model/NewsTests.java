package com.uvic.venus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NewsTests {
    
    @Test
    public void testNewsWithAllValidParameters() {
        // Given & When
        News news = new News(
            1,
            "testtitle",
            "testbodytext",
            "testnewsdate",
            21321312,
            "testauthor"
        );

        // Then
        assertEquals(1, news.getNewsId());
        assertEquals("testtitle", news.getTitle());
        assertEquals("testbodytext", news.getBodyText());
        assertEquals("testnewsdate", news.getNewsDate());
        assertEquals(21321312, news.getTimePublished());
        assertEquals("testauthor", news.getAuthor());
    }

    @Test
    public void testNewsWithNullParameters() {
        // Given
        News news = new News();
        
        // When
        news.setAuthor("testauthor");

        // Then
        assertEquals(null, news.getNewsId());
        assertEquals(null, news.getTitle());
        assertEquals(null, news.getBodyText());
        assertEquals(null, news.getNewsDate());
        assertEquals(null, news.getTimePublished());
        assertEquals("testauthor", news.getAuthor());
    }
}
