package com.uvic.venus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<?> addNews(@RequestBody News news) {
        newsDAO.save(news);
        return ResponseEntity.ok("Successfully add news article");
    }
}
