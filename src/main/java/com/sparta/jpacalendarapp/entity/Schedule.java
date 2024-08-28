package com.sparta.jpacalendarapp.entity;

import com.sparta.jpacalendarapp.dto.schedule.request.PostScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.request.PutScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.response.WeatherResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule extends Timestamped{

    private String content;
    private String title;
    private String weather;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ScheduleAssignment> scheduleAssignments = new ArrayList<>();

    public Schedule(PostScheduleRequestDto request, User user, String weather) {
        this.user = user;
        this.title = request.getTitle();
        this.content = request.getContent();
        this.weather = weather;
    }

    public void update(PutScheduleRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
    public void addComment(Comment comment) {
        if (comment.getSchedule() != this) {
            comment = new Comment(comment.getContent(), comment.getName(), comment.getSchedule());
        }
        this.commentList.add(comment);
    }
}
