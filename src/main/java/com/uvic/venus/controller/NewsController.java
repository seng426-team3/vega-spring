package com.uvic.venus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.uvic.venus.model.News;
import com.uvic.venus.repository.NewsDAO;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsDAO newsDAO;

    @RequestMapping(value="/fetchnews", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllNews() {
        List<News> newsList = newsDAO.findAll();
        return ResponseEntity.ok(newsList);
    }

    @RequestMapping(value="/addnews", method = RequestMethod.POST)
    public ResponseEntity<String> addNews(@RequestBody News news) {
        newsDAO.save(news);
        return ResponseEntity.ok("Successfully add news article");
    }

    @RequestMapping(value="/editnews", method=RequestMethod.POST)
    public ResponseEntity<String> editNews(@RequestPart Integer newsid, @RequestPart String title, @RequestPart String bodytext, @RequestPart String author) {
        News news = newsDAO.getById(newsid);

        // Update news element for all non-null provided parameters
        if (title != null) {
            news.setTitle(title);
        }

        if (bodytext != null) {
            news.setBodyText(bodytext);
        }

        if (author != null) {
            news.setAuthor(author);
        }

        newsDAO.deleteById(newsid);
        newsDAO.save(news);
        return ResponseEntity.ok("Successfully edited news article");
    }

    @GetMapping("/deletenews/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Integer newsid) {        
        newsDAO.deleteById(newsid);
        return ResponseEntity.ok("Successfully deleted news article");
    }
    
}
