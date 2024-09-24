package com.ksy.adapter.utils;

import com.ksy.adapter.model.WeatherDataDTO;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class WeatherDataMapper {

    public WeatherDataDTO mapToWeatherData(Map<String, Object> body) {

        Map<String, Object> dateMap = (Map<String, Object>) body.get("date");
        String dateString = (String) dateMap.get("UTC");
        ZonedDateTime date = ZonedDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> temperatureMap = (Map<String, Object>) ((Map<String, Object>) body.get("temperature")).get("air");
        Integer temperature = Integer.valueOf(temperatureMap.get("C").toString());

        WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
        weatherDataDTO.setDate(date);
        weatherDataDTO.setTemp(temperature);

        return weatherDataDTO;
    }
}

