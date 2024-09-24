package com.ksy.adapter.controller;

import com.ksy.adapter.exception.WeatherServiceException;
import com.ksy.adapter.model.IncomingMessageDTO;
import com.ksy.adapter.service.MessageService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/adapter")
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Adapter API",
                version = "1.0",
                description = "API for processing messages from service A and sending them to service B"),
        servers = {
                @Server(url = "${server.servlet.context-path}", description = "Local server"),
        }
)
@Tag(
        name = "Adapter Controller",
        description = "API for processing messages from service A and sending them to service B"
)
public class AdapterController {
    private final MessageService messageService;

    @Operation(summary = "Process and send a message to Service B",
            description = "Processes a message from Service A, enriches it with weather data, and sends it to Service B. The message must be in Russian.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message processed successfully and sent to Service B"),
            @ApiResponse(responseCode = "204", description = "Message should be in Russian language"),
            @ApiResponse(responseCode = "400", description = "Invalid message format"),
            @ApiResponse(responseCode = "503", description = "Weather service unavailable"),
            @ApiResponse(responseCode = "500", description = "Error processing message")
    })
    @PostMapping(value = "/message")
    private ResponseEntity<String> processMsg(@RequestBody @Valid IncomingMessageDTO incomingMessage) {
        try {
            if (!messageService.checkLanguage(incomingMessage.getLng())) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Message should be in Russian language");
            }
            messageService.processMessage(incomingMessage);
            return ResponseEntity.ok("Message processed successfully and sent to Service B");
        } catch (WeatherServiceException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Weather service unavailable");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing message");
        }
    }
}
