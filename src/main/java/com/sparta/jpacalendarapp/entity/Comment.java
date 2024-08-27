package com.sparta.jpacalendarapp.entity;

import com.sparta.jpacalendarapp.dto.comment.request.PutCommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    private String content;
    private String name;

    @ManyToOne
    @JoinColumn(name = "Schedule_id")
    private Schedule schedule;

    public Comment(String content, String name, Schedule schedule) {
        this.content = content;
        this.name = name;
        this.schedule = schedule;
    }

    public void update(PutCommentRequestDto request) {
        this.content = request.getContent();
    }
}
