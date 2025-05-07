package com.example.api_v01.utils;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.dto.RegisterATMDTO;
import com.example.api_v01.model.ATM;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.User;


public class ATMMovement {
    public static ATM saveATM(AtmDTO atm, Admin admin)  {
        return ATM.builder()
                .name_atm(atm.getName_atm())
                .date(atm.getDate())
                .alias(atm.getAlias())
                .email(atm.getEmail())
                .phone(atm.getPhone())
                .dni(atm.getDni())
                .user_atm(null)
                .admin(admin)
                .build();
    }

    public static ATM AssignUser(ATM atm,RegisterATMDTO register){
        User user = User.builder()
                .username(register.getUsername())
                .password(register.getPassword())
                .role(register.getRole())
                .build();
        atm.setUser_atm(user);
        return atm;
    }
}
