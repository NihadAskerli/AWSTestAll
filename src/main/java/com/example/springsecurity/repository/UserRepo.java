package com.example.springsecurity.repository;

import com.example.springsecurity.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
boolean existsByEmail(String email);
}
