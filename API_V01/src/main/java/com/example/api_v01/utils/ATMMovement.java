package com.example.api_v01.utils;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.dto.RegisterATMDTO;
import com.example.api_v01.model.ATM;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.User;

import java.time.LocalDate;


public class ATMMovement {

    public static ATM saveATM(AtmDTO atm, Admin admin)  {
        return ATM.builder()
                .name_atm(atm.getName_atm())
                .date(LocalDate.now())
                .alias(atm.getAlias())
                .email(atm.getEmail())
                .phone(atm.getPhone())
                .dni(atm.getDni())
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

    public static ATM validateATM(ATM atm,AtmDTO atmDTO) {
        if(atmDTO.getName_atm() != null){
            atm.setName_atm(atmDTO.getName_atm());
        }
        if(atm.getDate() != null){
            atm.setDate(atm.getDate());
        }
        if(atm.getAlias() != null){
            atm.setAlias(atm.getAlias());
        }
        if(atm.getEmail() != null){
            atm.setEmail(atm.getEmail());
        }
        if(atm.getPhone() != null){
            atm.setPhone(atm.getPhone());
        }
        if(atm.getDni() != null){
            atm.setDni(atm.getDni());
        }
        return atm;
    }
}
