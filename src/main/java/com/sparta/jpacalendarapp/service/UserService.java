package com.sparta.jpacalendarapp.service;

import com.sparta.jpacalendarapp.config.PasswordEncoder;
import com.sparta.jpacalendarapp.dto.user.request.PostUserLoginRequestDto;
import com.sparta.jpacalendarapp.dto.user.request.PostUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.request.UpdateUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.response.GetAllUserResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.GetUserResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.PostUserLoginResponseDto;
import com.sparta.jpacalendarapp.dto.user.response.PostUserResponseDto;
import com.sparta.jpacalendarapp.entity.User;
import com.sparta.jpacalendarapp.entity.UserRoleEnum;
import com.sparta.jpacalendarapp.exception.UserOrPasswordNotFoundException;
import com.sparta.jpacalendarapp.jwt.JwtUtil;
import com.sparta.jpacalendarapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /* 유저 등록 */
    public PostUserResponseDto createUser(PostUserRequestDto request) {
        String password = passwordEncoder.encode(request.getPassword());
        UserRoleEnum role = request.getUserRole() != null ? request.getUserRole() : UserRoleEnum.USER;
        User user = new User(request, password, role);
        String token = jwtUtil.createToken(request.getEmail());

        PostUserResponseDto responseDto = new PostUserResponseDto(userRepository.save(user), token);

        return responseDto;
    }

    /* 유저 로그인 */
    public PostUserLoginResponseDto loginUser(PostUserLoginRequestDto request) {
        String userEmail = request.getEmail();
        String userPwd = request.getPassword();

        // 유저 이메일 가져오기
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserOrPasswordNotFoundException("이메일을 찾을 수 없습니다."));

        // 유저 비밀번호 맞는지 확인
        if (!passwordEncoder.matches(userPwd, user.getPassword())) {
            throw new UserOrPasswordNotFoundException("비밀번호가 맞지 않습니다.");
        }
        String token = jwtUtil.createToken(request.getEmail());

        PostUserLoginResponseDto responseDto = new PostUserLoginResponseDto(user ,token);

        return responseDto;
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
        User user = findUser(id);

        user.update(request);

        return id;
    }

    /* 유저 삭제 */
    public Long deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return id;
    }

    /* 유저 ID 조회 */
    private User findUser(Long id) {
        return userRepository.findByIdOrElseThrow(id);
    }
}
