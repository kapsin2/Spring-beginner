package com.sparta.posting.dto;

import lombok.Getter;

@Getter
public class DeleteResponseDto {
    private String msg = "게시물 삭제 성공";

    private int statusCode = 200;
}