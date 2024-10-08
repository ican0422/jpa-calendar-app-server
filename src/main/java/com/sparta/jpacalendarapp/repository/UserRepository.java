package com.sparta.jpacalendarapp.repository;

import com.sparta.jpacalendarapp.entity.User;
import com.sparta.jpacalendarapp.exception.UserOrPasswordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new UserOrPasswordNotFoundException("유저을 찾을 수 없습니다.")
        );
    }
}
