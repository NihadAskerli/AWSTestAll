package com.example.springsecurity.repo;

import com.example.springsecurity.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo,Long> {
    Photo getById(Long id);

//    List<Photo>getAll();
}
