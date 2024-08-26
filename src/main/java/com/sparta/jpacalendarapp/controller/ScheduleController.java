package com.sparta.jpacalendarapp.controller;

import com.sparta.jpacalendarapp.dto.schedule.request.PostScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.request.PutScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.response.GetAllScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.GetScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.PostScheduleResponseDto;
import com.sparta.jpacalendarapp.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 사용자가 일정을 등록하는 컨트롤러
     * @param request 사용자 이름, 일정 제목, 일정 내용
     * @return 등록된 일정 id, 사용자 이름, 일정 제목, 일정 내용, 등록 시작, 수정 시간
     */
    @PostMapping("/schedules")
    public PostScheduleResponseDto createSchedule(@RequestBody PostScheduleRequestDto request) {
        return scheduleService.createSchedule(request);
    }

    @GetMapping("/schedules/{id}")
    public List<GetScheduleResponseDto> getOneSchedule(@PathVariable Long id) {
        return scheduleService.getOneSchedule(id);
    }

    @GetMapping("/schedules")
    public Page<GetAllScheduleResponseDto> getAllSchedule(
            @PageableDefault(page = 0,
                    size = 10,
                    sort = "modifiedDate",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return scheduleService.getAllSchedule(pageable);
    }

    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody PutScheduleRequestDto request) {
        return scheduleService.updateSchedule(id, request);
    }
}
