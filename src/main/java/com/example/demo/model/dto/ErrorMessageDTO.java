package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageDTO {
	@JsonProperty("errorCode")
    private String errorCode;
	
	@JsonProperty("message")
    private String message;

    @JsonProperty("path")
    private String path;

    @JsonProperty("dateTime")
    private String dateTime;
}
