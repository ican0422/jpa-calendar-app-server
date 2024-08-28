package com.sparta.jpacalendarapp.dto.user.request;

import lombok.Getter;

@Getter
public class PostUserLoginRequestDto {
    private String email;
    private String password;
}
