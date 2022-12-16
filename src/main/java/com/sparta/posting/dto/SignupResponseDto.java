package com.sparta.posting.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String msg = "회원가입 성공";

    private int statusCode = 200;
}
