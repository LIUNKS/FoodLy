package com.example.api_v01.dto.response;

import com.example.api_v01.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterAtmDTO {
    private String username;
    private String password;
    private Rol role;
}
