package com.example.api_v01.utils;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.model.ATM;


public class ATMMovement {
    public static ATM saveATM(AtmDTO atm) {
        return ATM.builder()
                .name_atm(atm.getName_atm())
                .date(atm.getDate())
                .alias(atm.getAlias())
                .email(atm.getEmail())
                .phone(atm.getPhone())
                .dni(atm.getDni())
                .user_atm(null)
                .admin(null)
                .build();
    }
}
