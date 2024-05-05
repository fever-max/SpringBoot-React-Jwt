package com.study.back.config.jwt;

public interface JwtProperties {

    String SECRET = "비밀키입니다.";
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}
