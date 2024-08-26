package com.sparta.jpacalendarapp.service;

import com.sparta.jpacalendarapp.dto.schedule.request.PostScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.request.PutScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.response.GetAllScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.GetScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.PostScheduleResponseDto;
import com.sparta.jpacalendarapp.entity.Schedule;
import com.sparta.jpacalendarapp.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public PostScheduleResponseDto createSchedule(PostScheduleRequestDto request) {
        // dto -> entity 변환
        Schedule schedule = new Schedule(request);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // entity -> dto 변환
        PostScheduleResponseDto responseDto = new PostScheduleResponseDto(saveSchedule);

        return responseDto;
    }

    public List<GetScheduleResponseDto> getOneSchedule(Long id) {
        return scheduleRepository.findAllById(id).stream().map(GetScheduleResponseDto::new).toList();
    }

    @Transactional
    public Long updateSchedule(Long id, PutScheduleRequestDto request) {
        Schedule schedule = findSchedule(id);

        schedule.update(request);

        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

    public Page<GetAllScheduleResponseDto> getAllSchedule(Pageable pageable) {
        return scheduleRepository.findAll(pageable)
                .map(GetAllScheduleResponseDto::new);
    }
}
