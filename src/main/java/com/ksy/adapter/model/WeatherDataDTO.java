package com.ksy.adapter.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class WeatherDataDTO {
    private ZonedDateTime date;
    private Integer temp;
}
