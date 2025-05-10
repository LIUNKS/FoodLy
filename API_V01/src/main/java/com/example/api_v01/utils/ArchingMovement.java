package com.example.api_v01.utils;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.Box;

import java.time.LocalDate;
import java.time.LocalTime;

public class ArchingMovement {
    public static Arching CreateArching(Box box, ArchingDTO archingDTO) {
        return Arching.builder()
                .date(LocalDate.now())
                .star_time(LocalTime.now())
                .init_amount(archingDTO.getInit_amount())
                .box(box)
                .build();
    }

    public static Arching CloseArchingBox(Arching arching,Double Final_amount) {
        arching.setEnd_time(LocalTime.now());
        arching.setFinal_amount(Final_amount);
        arching.setTotal_amount( arching.getInit_amount() + Final_amount );
        return arching;
    }
}
