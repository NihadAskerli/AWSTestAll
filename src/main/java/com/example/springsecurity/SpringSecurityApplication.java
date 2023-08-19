package com.example.springsecurity;


import com.example.springsecurity.models.entities.UserEntity;
import com.example.springsecurity.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@RequiredArgsConstructor
public class SpringSecurityApplication implements CommandLineRunner {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//userService.insert(new UserEntity(null,"company@gmail.com", passwordEncoder.encode("test"),"ROLE_COMPANY"));
    }
}
