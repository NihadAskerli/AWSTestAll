package com.example.springsecurity.service.user;

import com.example.springsecurity.models.entities.UserEntity;

public interface UserService {

    UserEntity save(UserEntity user);

    UserEntity getByEmail(String email);

    boolean checkEmail(String email);
}
