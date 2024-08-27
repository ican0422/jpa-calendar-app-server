package com.sparta.jpacalendarapp.repository;

import com.sparta.jpacalendarapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllById(Long id);
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("일정을 찾을 수 없습니다.")
        );
    }
}
