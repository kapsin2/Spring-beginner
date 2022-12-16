package com.sparta.posting.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String msg = "로그인성공";

    private int statusCode = 200;
}
