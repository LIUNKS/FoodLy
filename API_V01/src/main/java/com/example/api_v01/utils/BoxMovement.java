package com.example.api_v01.utils;

import com.example.api_v01.dto.BoxDTO;
import com.example.api_v01.model.Box;

public class BoxMovement {
    public static Box CreateBox(BoxDTO boxDTO) {
        return Box.builder()
                .name_box(boxDTO.getName_box())
                .date(boxDTO.getDate())
                .atm(null)
                .admin(null)
                .build();
    }
}
