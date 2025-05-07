package com.example.api_v01.dto;

import com.example.api_v01.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterATMDTO {
    private String username;
    private String password;
    private Rol role;
}
