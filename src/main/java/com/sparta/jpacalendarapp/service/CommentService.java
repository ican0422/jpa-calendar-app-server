package com.sparta.jpacalendarapp.service;

import com.sparta.jpacalendarapp.dto.comment.request.PostCommentRequestDto;
import com.sparta.jpacalendarapp.dto.comment.request.PutCommentRequestDto;
import com.sparta.jpacalendarapp.dto.comment.response.GetAllCommentResponseDto;
import com.sparta.jpacalendarapp.dto.comment.response.GetResponseDto;
import com.sparta.jpacalendarapp.dto.comment.response.PostCommentResponseDto;
import com.sparta.jpacalendarapp.entity.Comment;
import com.sparta.jpacalendarapp.entity.Schedule;
import com.sparta.jpacalendarapp.repository.CommentRepository;
import com.sparta.jpacalendarapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    /* 댓글 등록 */
    public PostCommentResponseDto createComment(Long sid, PostCommentRequestDto request) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(sid);

        Comment comment = new Comment(request.getContent(), request.getName(), schedule);

        schedule.addComment(comment);

        PostCommentResponseDto responseDto = new PostCommentResponseDto(commentRepository.save(comment));

        return responseDto;
    }

    /* 댓글 단건 조회 */
    public List<GetResponseDto> getOneComment(Long sid, Long cid) {
        return commentRepository.findByscheduleIdAndId(sid,cid).stream().map(GetResponseDto::new).toList();
    }

    /* 댓글 다건 조회 */
    public List<GetAllCommentResponseDto> getAllComment(Long sid) {
        return commentRepository.findAllByscheduleId(sid).stream().map(GetAllCommentResponseDto::new).toList();
    }

    /* 댓글 수정 */
    @Transactional
    public Long updateComment(Long cid, PutCommentRequestDto request) {
        // 댓글 ID 조회
        Comment comment = findComment(cid);
        comment.update(request);

        return cid;
    }

    /* 댓글 삭제 */
    public Long deleteComment(Long cid) {
        // 댓글 ID 조회
        Comment comment = findComment(cid);
        commentRepository.delete(comment);

        return cid;
    }

    /* 댓글 ID 조회 */
    private Comment findComment(Long cid) {
        return commentRepository.findByIdOrElseThrow(cid);
    }
}
