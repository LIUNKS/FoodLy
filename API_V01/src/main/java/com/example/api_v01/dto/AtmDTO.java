package com.example.api_v01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AtmDTO {

    private String name_atm;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String alias;

    private String email;

    private String phone;

    private String dni;

    private UserDTO userDTO;

    private AdminDTO adminDTO;
}
