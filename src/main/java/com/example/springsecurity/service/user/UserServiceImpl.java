package com.example.springsecurity.service.user;

import com.example.springsecurity.exception.BaseException;
import com.example.springsecurity.models.entities.UserEntity;

import com.example.springsecurity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    @Override
    public UserEntity save(UserEntity user) {
         return userRepository.save(user);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> BaseException.notFound(UserEntity.class.getSimpleName(), "email", email)
        );
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
