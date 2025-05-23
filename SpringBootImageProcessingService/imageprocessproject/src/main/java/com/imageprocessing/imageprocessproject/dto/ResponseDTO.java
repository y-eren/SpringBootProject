package com.imageprocessing.imageprocessproject.dto;


import com.imageprocessing.imageprocessproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private String jwt;
    private User user;
}
