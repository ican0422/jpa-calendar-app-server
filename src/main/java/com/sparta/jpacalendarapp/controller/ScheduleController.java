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
     * @param request 사용자 이름, 일정 제목, 일정 내용, 담당 유저 리스트
     * @return 등록된 일정 id, 사용자 이름, 일정 제목, 일정 내용, 등록 시작, 수정 시간
     */
    @PostMapping("/schedules")
    public PostScheduleResponseDto createSchedule(@RequestBody PostScheduleRequestDto request) {
        return scheduleService.createSchedule(request);
    }

    /**
     * 일정의 단건 조회 컨트롤러
     * @param id 일정 id를 받는다
     * @return 단건 조회한 일정을 보여준다.
     */
    @GetMapping("/schedules/{id}")
    public List<GetScheduleResponseDto> getOneSchedule(@PathVariable Long id) {
        return scheduleService.getOneSchedule(id);
    }

    /**
     * 사용자가 입력한 일정의 다건 조회(페이징 기능 활성화)
     * @param pageable 페이지 혹은 사이즈를 받아서 조회 기본 페이지 사이즈는 10
     * @return 페이지 값에 데이터 갯수 리턴, 기본 사이즈는 10, 수정날짜에 따른 내림차순 정렬
     */
    @GetMapping("/schedules")
    public Page<GetAllScheduleResponseDto> getAllSchedule(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "modifiedDate",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return scheduleService.getAllSchedule(pageable);
    }

    /**
     * 사용자의 일정을 수정한다. 일정 제목, 일정 내용을 수정 받는다.
     * @param id 수정할 일정 ID, 일정 제목, 일정 내용, 담당 유저 리스트
     * @param request 일정의 제목, 일정의 내용을 받는다.
     * @return 수정된 일정 ID
     */
    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody PutScheduleRequestDto request) {
        return scheduleService.updateSchedule(id, request);
    }

    /**
     * 사용자 일정을 삭제(영속성 전이)
     * @param id 삭제할 일정 ID
     * @return 삭제된 일정 ID
     */
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        return scheduleService.deleteSchedile(id);
    }
}
