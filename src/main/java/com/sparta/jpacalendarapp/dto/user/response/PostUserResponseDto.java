package com.sparta.jpacalendarapp.dto.user.response;

import com.sparta.jpacalendarapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String token;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;

    public PostUserResponseDto(User user, String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.token = token;
        this.creationDate = user.getCreationDate();
        this.modifiedDate = user.getModifiedDate();
    }
}
