package com.sparta.jpacalendarapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scheduleAssignment")
@Getter
@NoArgsConstructor
public class ScheduleAssignment extends Timestamped{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public ScheduleAssignment(Schedule schedule, User user) {
        this.schedule = schedule;
        this.user = user;
    }
}
