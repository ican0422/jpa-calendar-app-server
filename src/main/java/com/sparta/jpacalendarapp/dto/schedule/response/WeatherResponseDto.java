package com.sparta.jpacalendarapp.dto.schedule.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponseDto {

    @JsonProperty("date")
    private String date;

    @JsonProperty("weather")
    private String weather;
}
