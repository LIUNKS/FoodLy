package com.example.api_v01.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminDTO {

    private String name_admin;

    private String email_admin;

    private String dni_admin;

    private UserDTO user;

}
