package com.example.api_v01.service.atm_service;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.dto.RegisterATMDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;

import java.util.List;
import java.util.UUID;

public interface ATMService {
    ATM saveATM(UUID id_admin,AtmDTO atm) throws NotFoundException;
    ATM assingUserATM(UUID id_atm, RegisterATMDTO atm) throws NotFoundException;
    ATM updateATM(UUID id_atm,AtmDTO atm) throws NotFoundException;
    void deleteATM(UUID id_atm) throws NotFoundException;
    ATM getAtmById(UUID id_atm) throws NotFoundException;
    List<ATM> getAllATMs();
}
