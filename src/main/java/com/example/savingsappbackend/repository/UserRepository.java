package com.example.savingsappbackend.repository;

import com.example.savingsappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wallet WHERE u.id = :userId")
    Optional<User> findByIdWithWallet(@Param("userId") Long userId);
}
