package com.ksy.adapter.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OutgoingMessageDTO {
    private String txt;
    private ZonedDateTime createdDt;
    private Integer currentTemp;
}
