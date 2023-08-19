package com.example.springsecurity.service.user;

import com.example.springsecurity.models.entities.UserEntity;
import com.example.springsecurity.models.security.LoggedInUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getByEmail(username);
        List<SimpleGrantedAuthority> authorityList=new ArrayList<>();
      authorityList.add(new SimpleGrantedAuthority(user.getRole()));
        return new LoggedInUserDetails(
                user.getEmail(), user.getPassword(),authorityList
        );
    }
}
