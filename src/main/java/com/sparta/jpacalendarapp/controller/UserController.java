package com.sparta.jpacalendarapp.controller;

import com.sparta.jpacalendarapp.dto.user.request.PostUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.request.UpdateUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.response.GetAllUserResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.GetUserResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.PostUserResponseDto;
import com.sparta.jpacalendarapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 유저 생성
     * @param request 유저 이름, 유저 이메일
     * @return 유저 아이디, 유저 이름, 유저 이메일, 생성일, 수정일
     */
    @PostMapping("/users")
    public PostUserResponseDto createUser(@RequestBody PostUserRequestDto request) {
        return userService.createUser(request);
    }

    /**
     * 유저 단건 조회
     * @param id 유저 아이디
     * @return 유저 아이디, 유저 이름, 유저 이메일, 생성일, 수정일
     */
    @GetMapping("/users/{id}")
    public List<GetUserResponseDto> getOneUser(@PathVariable Long id) {
        return userService.getOneUser(id);
    }

    /**
     * 유저 다건 조회
     * @return 유저 아이디, 유저 이름, 유저 이메일, 생성일, 수정일
     */
    @GetMapping("/users")
    public List<GetAllUserResponseDto> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * 유저 수정
     * @param id 수정할 유저 ID
     * @param request 유저 이름, 유저 이메일
     * @return 수정된 유저 ID
     */
    @PutMapping("/users/{id}")
    public Long updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto request) {
        return userService.updateUser(id, request);
    }

    /**
     * 유저 삭제
     * @param id 삭제할 유저 ID
     * @return 삭제된 유저 ID
     */
    @DeleteMapping("/users/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
