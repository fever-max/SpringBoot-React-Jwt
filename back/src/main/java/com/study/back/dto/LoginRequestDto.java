package com.study.back.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String userEmail;
    private String password;
}
