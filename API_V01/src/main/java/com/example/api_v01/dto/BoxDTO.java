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
public class BoxDTO {
    private String name_box;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Boolean is_open;

    private AtmDTO atmDTO;

    private AdminDTO adminDTO;
}
