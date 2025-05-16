package com.example.api_v01.utils;

import com.example.api_v01.dto.entityLike.AtmDTO;
import com.example.api_v01.dto.response.AtmResponseDTO;
import com.example.api_v01.dto.response.RegisterAtmDTO;
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

    public static ATM AssignUser(ATM atm, RegisterAtmDTO register){
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

    public static AtmResponseDTO convertToResponseDTO(ATM atm){
        return AtmResponseDTO.builder()
                .name_atm(atm.getName_atm())
                .alias(atm.getAlias())
                .email(atm.getEmail())
                .phone(atm.getPhone())
                .dni(atm.getDni())
                .build();
    }

    public static AtmDTO convertToDTO(ATM atm){
        return AtmDTO.builder()
                .id_atm(atm.getId_atm())
                .name_atm(atm.getName_atm())
                .alias(atm.getAlias())
                .email(atm.getEmail())
                .phone(atm.getPhone())
                .dni(atm.getDni())
                .build();
    }

    public static ATM convertDTOToATM(AtmDTO atmDTO) {
        return ATM.builder()
                .id_atm(atmDTO.getId_atm()) // Mapea los campos relevantes
                .name_atm(atmDTO.getName_atm())
                .alias(atmDTO.getAlias())
                .email(atmDTO.getEmail())
                .phone(atmDTO.getPhone())
                .dni(atmDTO.getDni())
                .build();
    }


}
