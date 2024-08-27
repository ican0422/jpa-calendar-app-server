package com.sparta.jpacalendarapp.dto.schedule.response;

import com.sparta.jpacalendarapp.entity.ScheduleAssignment;
import lombok.Getter;

@Getter
public class AssignmentUserDto {
    private Long id;
    private String name;
    private String email;

    public AssignmentUserDto(ScheduleAssignment assignment) {
        this.id = assignment.getUser().getId();
        this.name = assignment.getUser().getName();
        this.email = assignment.getUser().getEmail();
    }
}
