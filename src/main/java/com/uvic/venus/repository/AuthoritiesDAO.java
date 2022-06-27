package com.uvic.venus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uvic.venus.model.Authorities;

@Repository
public interface AuthoritiesDAO extends JpaRepository<Authorities, String> {
}
