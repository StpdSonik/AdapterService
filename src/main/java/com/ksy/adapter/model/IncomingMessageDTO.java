package com.ksy.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Incoming message object containing the message and its metadata")
public class IncomingMessageDTO {
    @NotBlank(message = "Message field 'msg' cannot be empty")
    @Schema(description = "The main message content", example = "Hello, this is a test message")
    private String msg;

    @NotBlank(message = "Message field 'lng' cannot be empty")
    @Schema(description = "Language code, only 'ru' is supported", example = "ru")
    private String lng;

    @Valid
    @Schema(description = "Geographical coordinates", required = true)
    private CoordinatesDTO coordinates;
}
