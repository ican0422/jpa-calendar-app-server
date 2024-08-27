package com.sparta.jpacalendarapp.dto.comment.response;

import com.sparta.jpacalendarapp.entity.Comment;
import com.sparta.jpacalendarapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCommentResponseDto {
    private Long id;
    private String content;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private Schedule schedule;

    public PostCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getName();
        this.creationDate = comment.getCreationDate();
        this.modifiedDate = comment.getModifiedDate();
        this.schedule = comment.getSchedule();
    }
}
