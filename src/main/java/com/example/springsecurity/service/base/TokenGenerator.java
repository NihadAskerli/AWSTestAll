package com.example.springsecurity.service.base;

public interface TokenGenerator <T> {

    String generate(T obj);

}
