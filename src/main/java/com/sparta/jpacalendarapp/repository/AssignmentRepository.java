package com.sparta.jpacalendarapp.repository;

import com.sparta.jpacalendarapp.entity.ScheduleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<ScheduleAssignment, Long> {
}
