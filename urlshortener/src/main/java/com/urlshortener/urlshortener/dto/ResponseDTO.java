package com.urlshortener.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private String originalUrl;
    private String shortCode;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
