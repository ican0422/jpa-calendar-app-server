package com.sparta.jpacalendarapp.dto.schedule.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PutScheduleRequestDto {
    private String title;
    private String content;
    private List<Long> userIds;
}
