package com.uvic.venus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uvic.venus.model.News;

@Repository
public interface NewsDAO extends JpaRepository<News, String> {
}
