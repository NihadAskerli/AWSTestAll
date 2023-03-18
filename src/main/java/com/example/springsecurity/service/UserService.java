package com.example.springsecurity.service;

import com.example.springsecurity.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    public static final String email="test@gmail.com";
    public static final String anotheremail="nihad@gmail.com";
    public Optional<UserEntity> findByEmail(String username){
        if(email.equalsIgnoreCase(username)) {
            var user = new UserEntity();
            user.setId(1l);
            user.setEmail(email);
            user.setPassword("$2a$10$5v0s0Y86gP7pudSJouCvOe8yr8s4CaO5kTijZzcvb7fwcVxDVG3Ce");//test
            user.setRole("ROLE_ADMIN");
            user.setExtraInfo("My nice admin");
            return Optional.of(user);
        }else if(anotheremail.equalsIgnoreCase(username)) {
            var user = new UserEntity();
            user.setId(2l);
            user.setEmail(anotheremail);
            user.setPassword("$2a$10$5v0s0Y86gP7pudSJouCvOe8yr8s4CaO5kTijZzcvb7fwcVxDVG3Ce");//test
            user.setRole("ROLE_USER");
            user.setExtraInfo("My nice user");
            return Optional.of(user);
        }
        return Optional.empty();

    }
}
