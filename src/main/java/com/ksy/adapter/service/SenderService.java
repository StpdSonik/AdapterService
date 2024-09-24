package com.ksy.adapter.service;

import com.ksy.adapter.model.OutgoingMessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class SenderService {
    @Value("${service-b.url}")
    private static String serviceBUrl;
    private final RestTemplate restTemplate;

    /**
     *
     * @param message
     */
    void sendMessage(OutgoingMessageDTO message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OutgoingMessageDTO> request = new HttpEntity<>(message, headers);
        restTemplate.postForEntity(serviceBUrl, request, Void.class);
    }
}
