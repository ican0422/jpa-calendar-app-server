package com.sparta.jpacalendarapp.repository;

import com.sparta.jpacalendarapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllById(Long id);
}
