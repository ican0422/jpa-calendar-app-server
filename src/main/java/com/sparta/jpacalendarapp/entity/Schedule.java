package com.sparta.jpacalendarapp.entity;

import com.sparta.jpacalendarapp.dto.schedule.request.PostScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.request.PutScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "title", nullable = false)
    private String title;


    public Schedule(PostScheduleRequestDto request) {
        this.name = request.getName();
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void update(PutScheduleRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
