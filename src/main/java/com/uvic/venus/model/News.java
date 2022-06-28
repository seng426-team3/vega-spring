package com.uvic.venus.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class News {

    @Id
    private String newsid;
    private String title;
    private String bodytext;
    private String newsdate;
    private Integer timepublished;
    private String author;

    public News(String newsid, String title, String bodytext, String newsdate, Integer timepublished, String author) {
        this.newsid = newsid;
        this.title = title;
        this.bodytext = bodytext;
        this.newsdate = newsdate;
        this.timepublished = timepublished;
        this.author = author;
    }

    public News() {

    }

    public String getNewsId() {
        return newsid;
    }

    public void setNewsId(String newsid) {
        this.newsid = newsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyText() {
        return bodytext;
    }

    public void setBodyText(String bodytext) {
        this.bodytext = bodytext;
    }

    public String getNewsDate() {
        return newsdate;
    }
    
    public void setNewsDate(String newsdate) {
        this.newsdate = newsdate;
    }

    public Integer getTimePublished() {
        return timepublished;
    }

    public void setTimePublished(Integer timepublished) {
        this.timepublished = timepublished;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsid='" + newsid + '\'' +
                ", title='" + title + '\'' +
                ", bodytext='" + bodytext + '\'' +
                ", newsdate='" + newsdate + '\'' +
                ", timepublished='" + timepublished.toString() + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
