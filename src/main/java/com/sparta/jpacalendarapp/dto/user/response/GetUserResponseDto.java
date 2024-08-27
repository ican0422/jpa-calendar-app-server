package com.sparta.jpacalendarapp.dto.user.response;

import com.sparta.jpacalendarapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;

    public GetUserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.creationDate = user.getCreationDate();
        this.modifiedDate = user.getModifiedDate();
    }
}
