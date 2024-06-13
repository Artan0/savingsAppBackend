package com.example.savingsappbackend.repository;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    Page<Goal> findByOwner(User owner, Pageable pageable);

    Page<Goal> findByOwnerAndTitleContainingIgnoreCase(User owner, String title, Pageable pageable);

}
