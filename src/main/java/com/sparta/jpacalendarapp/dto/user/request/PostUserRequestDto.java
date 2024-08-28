package com.sparta.jpacalendarapp.dto.user.request;

import com.sparta.jpacalendarapp.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class PostUserRequestDto {
    private String name;
    private String email;
    private String password;
    private UserRoleEnum userRole;
}
