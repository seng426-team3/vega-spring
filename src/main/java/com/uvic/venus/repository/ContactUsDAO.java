package com.uvic.venus.repository;

import com.uvic.venus.model.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactUsDAO extends JpaRepository<ContactUs, String> {
}
