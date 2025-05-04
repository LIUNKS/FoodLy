package com.example.api_v01.dto;

import com.example.api_v01.model.Arching;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderSetDTO {

    private String name_client;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_order;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time_order;

    private Double total_order;

    private Arching arching;

    private Boolean dispatch;
}
