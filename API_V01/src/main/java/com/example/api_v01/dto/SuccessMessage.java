package com.example.api_v01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SuccessMessage <T> {
    private HttpStatus status;
    private String message;
    private T data;
}
