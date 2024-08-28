package com.sparta.jpacalendarapp.dto.user.response;

import com.sparta.jpacalendarapp.entity.User;
import lombok.Getter;

@Getter
public class PostUserLoginResponseDto {
    private String email;
    private String name;
    private String token;

    public PostUserLoginResponseDto(User user, String token) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.token = token;
    }
}
