package com.example.api_v01.dto;

import com.example.api_v01.model.Box;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class ArchingDTO {

    private UUID id_arching;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime star_time;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime end_time;

    private Double init_amount;

    private Double final_amount;

    private Double total_amount;

    private BoxDTO box;

}
