package com.example.api_v01.service.atm_service;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;

import java.util.List;
import java.util.UUID;

public interface ATMService {
    public ATM saveATM(AtmDTO atm);
    public ATM updateATM(UUID id_atm,AtmDTO atm) throws NotFoundException;
    public void deleteATM(UUID id_atm) throws NotFoundException;
    public ATM getAtmById(UUID id_atm) throws NotFoundException;
    public List<ATM> getAllATMs();
}
