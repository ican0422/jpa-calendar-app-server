package com.sparta.jpacalendarapp.repository;

import com.sparta.jpacalendarapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByscheduleIdAndId(Long sid, Long cid);
    List<Comment> findAllByscheduleId(Long sid);

    default Comment findByIdOrElseThrow(Long cid) {
        return findById(cid).orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없습니다."));
    }
}
