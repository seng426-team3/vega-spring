package com.uvic.venus.controller;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> addNews(@RequestBody Map<String, Object> newsToAddJSON) {
        News newsToAdd = new News();
        newsToAdd.setNewsId((Integer) newsToAddJSON.get("timepublished"));
        newsToAdd.setTitle(newsToAddJSON.get("title").toString());
        newsToAdd.setBodyText(newsToAddJSON.get("bodytext").toString());
        newsToAdd.setNewsDate(newsToAddJSON.get("newsdate").toString());
        newsToAdd.setTimePublished((Integer) newsToAddJSON.get("timepublished"));
        newsToAdd.setAuthor(newsToAddJSON.get("author").toString());
        
        newsDAO.save(newsToAdd);
        return ResponseEntity.ok("Successfully added news article");
    }

    @RequestMapping(value="/editnews", method=RequestMethod.POST)
    public ResponseEntity<String> editNews(@RequestBody Map<String, Object> newsToEditJSON) {
        News newsToAdd = new News();
        newsToAdd.setNewsId((Integer) newsToEditJSON.get("newsid"));
        newsToAdd.setTitle(newsToEditJSON.get("title").toString());
        newsToAdd.setBodyText(newsToEditJSON.get("bodytext").toString());
        newsToAdd.setNewsDate(newsToEditJSON.get("newsdate").toString());
        newsToAdd.setTimePublished((Integer) newsToEditJSON.get("timepublished"));
        newsToAdd.setAuthor(newsToEditJSON.get("author").toString());

        newsDAO.deleteById((Integer) newsToEditJSON.get("newsid"));
        newsDAO.save(newsToAdd);
        return ResponseEntity.ok("Successfully edited news article");
    }

    @RequestMapping(value="/deletenews", method=RequestMethod.POST)
    public ResponseEntity<String> deleteNews(@RequestBody Map<String, Object> newsToDeleteJSON) {        
        newsDAO.deleteById((Integer) newsToDeleteJSON.get("newsid"));
        return ResponseEntity.ok("Successfully deleted news article");
    }
    
}
