package com.ksy.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ErrorModel {
    @Schema(example = "Error")
    @NotBlank
    private List<String> errors;
    @Schema(example = "Message with error explanation")
    private List<String> messages = new ArrayList<>();
    @Schema(example = "400", description = "Error status")
    @Min(value = 400)
    private int status;

    public void addMessage(String str) {
        messages.add(str);
    }
}