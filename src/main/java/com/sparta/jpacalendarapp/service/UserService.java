package com.sparta.jpacalendarapp.service;

import com.sparta.jpacalendarapp.dto.user.request.PostUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.request.UpdateUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.response.GetAllUserResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.GetUserResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.PostUserResponseDto;
import com.sparta.jpacalendarapp.entity.User;
import com.sparta.jpacalendarapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* 유저 등록 */
    public PostUserResponseDto createUser(PostUserRequestDto request) {
        User user = new User(request);

        PostUserResponseDto requestDto = new PostUserResponseDto(userRepository.save(user));

        return requestDto;
    }

    /* 유저 단건 조회 */
    public List<GetUserResponseDto> getOneUser(Long id) {
        return userRepository.findById(id).stream().map(GetUserResponseDto::new).toList();
    }

    /* 유저 다건 조회 */
    public List<GetAllUserResponseDto> getAllUser() {
        return userRepository.findAll().stream().map(GetAllUserResponseDto::new).toList();
    }

    /* 유저 수정 */
    @Transactional
    public Long updateUser(Long id, UpdateUserRequestDto request) {
        User user = findSchedule(id);

        user.update(request);

        return id;
    }

    /* 유저 삭제 */
    public Long deleteUser(Long id) {
        User user = findSchedule(id);

        userRepository.delete(user);

        return id;
    }

    /* 유저 ID 조회 */
    private User findSchedule(Long id) {
        return userRepository.findByIdOrElseThrow(id);
    }
}
