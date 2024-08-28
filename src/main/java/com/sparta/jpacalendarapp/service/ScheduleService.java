package com.sparta.jpacalendarapp.service;

import com.sparta.jpacalendarapp.dto.schedule.request.PostScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.request.PutScheduleRequestDto;
import com.sparta.jpacalendarapp.dto.schedule.response.GetAllScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.GetScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.PostScheduleResponseDto;
import com.sparta.jpacalendarapp.dto.schedule.response.WeatherResponseDto;
import com.sparta.jpacalendarapp.entity.Schedule;
import com.sparta.jpacalendarapp.entity.ScheduleAssignment;
import com.sparta.jpacalendarapp.entity.User;
import com.sparta.jpacalendarapp.exception.UserOrPasswordNotFoundException;
import com.sparta.jpacalendarapp.repository.AssignmentRepository;
import com.sparta.jpacalendarapp.repository.ScheduleRepository;
import com.sparta.jpacalendarapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            AssignmentRepository assignmentRepository,
            UserRepository userRepository,
            RestTemplate restTemplate
    ) {
        this.scheduleRepository = scheduleRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    /* 일정 생성 */
    public PostScheduleResponseDto createSchedule(PostScheduleRequestDto request) {
        // 유저 ID 찾기
        User user = userRepository.findById(request.getUserId()).orElseThrow(()
                -> new UserOrPasswordNotFoundException("해당 유저가 없습니다."));

        WeatherResponseDto weatherResponseDto = getWeatherForToday();
        String weather = weatherResponseDto.getWeather();

        // dto -> entity 변환
        Schedule schedule = new Schedule(request, user,weather);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // 추가된 유저를 중간 테이블의 저장
        addUsersToSchedule(saveSchedule, request.getUserIds());

        // entity -> dto 변환
        PostScheduleResponseDto responseDto = new PostScheduleResponseDto(saveSchedule);

        return responseDto;
    }

    /* 일정 단건 조회 */
    public List<GetScheduleResponseDto> getOneSchedule(Long id) {
        return scheduleRepository.findAllById(id).stream().map(GetScheduleResponseDto::new).toList();
    }

    /* 일정 다건 조회(페이징) */
    public Page<GetAllScheduleResponseDto> getAllSchedule(Pageable pageable) {
        return scheduleRepository.findAll(pageable)
                .map(GetAllScheduleResponseDto::new);
    }

    /* 일정 수정 */
    @Transactional
    public Long updateSchedule(Long id, PutScheduleRequestDto request) {
        Schedule schedule = findSchedule(id);

        // 추가된 유저를 중간 테이블의 저장
        addUsersToSchedule(schedule, request.getUserIds());

        schedule.update(request);

        return id;
    }

    /* 일정 삭제(영속성 전이) */
    public Long deleteSchedile(Long id) {
        Schedule schedule = findSchedule(id);

        scheduleRepository.delete(schedule);

        return id;
    }

    /* 일정 ID 조회 */
    private Schedule findSchedule(Long id) {
        return scheduleRepository.findByIdOrElseThrow(id);
    }

    /* 추가된 유저를 중간 테이블의 저장 */
    private void addUsersToSchedule(Schedule schedule, List<Long> userIdList) {
        // 유저 List 받아온다. 받아온 데이터중 List안에 추가 유저 id가 null이라면 해당 로직은 실행되지 않는다.
        if (userIdList != null){
            // 유저 객체 담을 리스트
            List<User> usersToAdd = new ArrayList<>();
            // 유저 ID 리스트를 하나씩 조회 하면서 ID가 있다면 객체를 리스트에 담는다.
            for (Long userId : userIdList) {
                // ID가 있는지 확인
                User plusUser = userRepository.findByIdOrElseThrow(userId);
                // 있다면 객체를 리스트에 추가
                usersToAdd.add(plusUser);
            }
            // 객체를 하나씩 조회하면서 중간 테이블에 저장
            for (User plusUser : usersToAdd) {
                // 중간 테이블 Entity 생성
                ScheduleAssignment assignment = new ScheduleAssignment(schedule, plusUser);
                // DB 저장
                assignmentRepository.save(assignment);
            }
        }
    }

    public WeatherResponseDto getWeatherForToday () {
        WeatherResponseDto[] response = restTemplate.getForObject(weatherApiUrl, WeatherResponseDto[].class);
        if (response == null || response.length == 0) {
            throw new IllegalStateException("날씨 정보를 가져오는데 실패 했습니다.");
        }
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MM-dd"));

        return Arrays.stream(response)
                .filter(weather -> today.equals(weather.getDate()))
                .findFirst()
                .orElseGet(() -> {
                    WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
                    weatherResponseDto.setDate(today);
                    weatherResponseDto.setWeather("날씨 정보 없음");
                    return weatherResponseDto;

                });

    }
}
