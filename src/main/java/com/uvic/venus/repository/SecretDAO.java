package com.uvic.venus.repository;

import com.uvic.venus.model.SecretEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretDAO extends JpaRepository<SecretEntry, String> {
}