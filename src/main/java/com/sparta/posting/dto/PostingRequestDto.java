package com.sparta.posting.dto;

import lombok.Getter;

@Getter
public class PostingRequestDto {
    private String postinghead;
    private String postingcontents;


    public String getPostinghead() {
        return postinghead;
    }

    public String getPostingcontents() {
        return postingcontents;
    }

}
