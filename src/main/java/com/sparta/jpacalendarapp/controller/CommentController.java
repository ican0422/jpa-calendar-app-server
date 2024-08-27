package com.sparta.jpacalendarapp.controller;

import com.sparta.jpacalendarapp.dto.comment.request.PostCommentRequestDto;
import com.sparta.jpacalendarapp.dto.comment.request.PutCommentRequestDto;
import com.sparta.jpacalendarapp.dto.comment.response.GetAllCommentResponseDto;
import com.sparta.jpacalendarapp.dto.comment.response.GetResponseDto;
import com.sparta.jpacalendarapp.dto.comment.response.PostCommentResponseDto;
import com.sparta.jpacalendarapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 등록 컨트롤러
     * @param request 댓글 내용, 댓글 작성 유저명
     * @return 댓글 아이디, 댓글 내용, 댓글 작성 유저명, 작성일, 수정일, 일정 id
     */
    @PostMapping("/schedules/{sid}/comments")
    public PostCommentResponseDto createComment(@PathVariable Long sid, @RequestBody PostCommentRequestDto request) {
        return commentService.createComment(sid, request);
    }

    /**
     * 댓글 단건 조회
     * @param sid 일정 ID
     * @param cid 댓글 ID
     * @return 조회한 댓글의 정보
     */
    @GetMapping("/schedules/{sid}/comments/{cid}")
    public List<GetResponseDto> getOneComment(@PathVariable Long sid, @PathVariable Long cid) {
        return commentService.getOneComment(sid, cid);
    }

    /**
     * 해당 일정에 달린 댓글 다건 조회
     * @param sid 일정 ID
     * @return 해당 일정에 달린 모든 댓글 정보
     */
    @GetMapping("/schedules/{sid}/comments")
    public List<GetAllCommentResponseDto> getAllComment(@PathVariable Long sid) {
        return commentService.getAllComment(sid);
    }

    /**
     * 해당 일정의 댓글 수정
     * @param sid 어떤 일정인지 보여주는 PathVariable
     * @param cid 수정할 댓글 ID
     * @param request 수정할 댓글 내용(content)
     * @return 성공한 댓글 ID 반환
     */
    @PutMapping("/schedules/{sid}/comments/{cid}")
    public Long updateComment(@PathVariable Long sid, @PathVariable Long cid, @RequestBody PutCommentRequestDto request) {
        return commentService.updateComment(cid, request);
    }

    @DeleteMapping("/schedules/{sid}/comments/{cid}")
    public Long deleteComment(@PathVariable Long sid, @PathVariable Long cid) {
        return commentService.deleteComment(cid);
    }
}
