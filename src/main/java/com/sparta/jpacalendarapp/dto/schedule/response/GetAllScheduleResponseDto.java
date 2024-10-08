package com.sparta.jpacalendarapp.dto.schedule.response;

import com.sparta.jpacalendarapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAllScheduleResponseDto {
    private Long id;
    private String name;
    private String content;
    private String weather;
    private LocalDateTime creation_Date;
    private LocalDateTime modified_Date;

    public GetAllScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getUser().getName();
        this.content = schedule.getContent();
        this.weather = schedule.getWeather();
        this.creation_Date = schedule.getCreationDate();
        this.modified_Date = schedule.getModifiedDate();
    }
}
