package com.example.savingsappbackend.repository;

import com.example.savingsappbackend.models.UserMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMailRepository  extends JpaRepository<UserMail, Long> {
}
