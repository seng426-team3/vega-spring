package com.uvic.venus.repository;

import com.uvic.venus.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<Users, String> {
    
}
