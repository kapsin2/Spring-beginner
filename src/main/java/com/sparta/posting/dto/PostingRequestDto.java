package com.sparta.posting.dto;

import lombok.Getter;

@Getter
public class PostingRequestDto {
    private String postinghead;
    private String postingcontents;
    private String password;
    private String name;

    public String getPostinghead() {
        return postinghead;
    }

    public String getPostingcontents() {
        return postingcontents;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
