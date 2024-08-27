package com.sparta.jpacalendarapp.dto.schedule.response;

import com.sparta.jpacalendarapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleResponseDto {
    private Long id;
    private String name;
    private String content;
    private LocalDateTime creation_Date;
    private LocalDateTime modified_Date;
    private List<AssignmentUserDto> userIds;

    public GetScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getUser().getName();
        this.content = schedule.getContent();
        this.creation_Date = schedule.getCreationDate();
        this.modified_Date = schedule.getModifiedDate();
        this.userIds = schedule.getScheduleAssignments().stream().map(AssignmentUserDto::new).toList();
    }
}
