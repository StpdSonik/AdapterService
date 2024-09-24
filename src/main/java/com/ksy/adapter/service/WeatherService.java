package com.ksy.adapter.service;

import com.ksy.adapter.exception.WeatherServiceException;
import com.ksy.adapter.model.WeatherDataDTO;
import com.ksy.adapter.utils.WeatherDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherDataMapper weatherDataMapper;

    @Value("${gismeteo.api.url}")
    private static String GISMETEO_API_URL;
    @Value("${gismeteo.api.token}")
    private static String GISMETEO_TOKEN;

    /**
     * Method for request to GISMeteo
     * @param latitude
     * @param longitude
     * @return weather data
     * @throws WeatherServiceException if weather service is unavailable
     */
    public WeatherDataDTO getWeatherForCoordinates(Double latitude, Double longitude) throws WeatherServiceException {
        String url = String.format("%s?latitude=%f&longitude=%f", GISMETEO_API_URL, latitude, longitude);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gismeteo-Token", GISMETEO_TOKEN);
            headers.set("Accept-Encoding", "gzip, deflate");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return weatherDataMapper.mapToWeatherData(Objects.requireNonNull(response.getBody()));
            } else {
                throw new WeatherServiceException("Failed to fetch weather data: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new WeatherServiceException("Weather service is unavailable. " + e.getMessage());
        }
    }
}
