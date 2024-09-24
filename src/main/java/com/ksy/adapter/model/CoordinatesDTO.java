package com.ksy.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoordinatesDTO {
    @NotBlank(message = "Message field 'latitude' cannot be empty")
    @Schema(description = "Latitude of the location", example = "55.7558")
    private Double latitude;
    
    @NotBlank(message = "Message field 'longitude' cannot be empty")
    @Schema(description = "Longitude of the location", example = "37.6173")
    private Double longitude;
}
