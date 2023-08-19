package com.example.springsecurity.service.base;

public interface TokenReader <T> {

    T read(String token);

}
