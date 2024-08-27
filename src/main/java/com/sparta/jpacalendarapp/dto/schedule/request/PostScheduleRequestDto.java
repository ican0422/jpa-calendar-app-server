package com.sparta.jpacalendarapp.dto.schedule.request;

import com.sparta.jpacalendarapp.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class PostScheduleRequestDto {
    private Long userId;
    private String title;
    private String content;
    private List<Long> userIds;
}
