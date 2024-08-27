package com.sparta.jpacalendarapp.entity;

import com.sparta.jpacalendarapp.dto.schedule.request.PostScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.request.PutScheduleRequestDto;
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

    private String name;
    private String content;
    private String title;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> commentList = new ArrayList<>();

    public Schedule(PostScheduleRequestDto request) {
        this.name = request.getName();
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void update(PutScheduleRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
    public void addComment(Comment comment) {
        this.commentList.add(comment);
        if (comment.getSchedule() != this) {
            comment = new Comment(comment.getContent(), comment.getName(), comment.getSchedule());
        }
    }
}
