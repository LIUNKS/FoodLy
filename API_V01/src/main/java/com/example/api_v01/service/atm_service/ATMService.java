package com.example.api_v01.service.atm_service;

import com.example.api_v01.dto.entityLike.AtmDTO;
import com.example.api_v01.dto.response.AtmResponseDTO;
import com.example.api_v01.dto.response.RegisterAtmDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;

import java.util.List;
import java.util.UUID;

public interface ATMService {
    AtmResponseDTO saveATM(UUID id_admin, AtmDTO atm) throws NotFoundException;
    AtmResponseDTO assingUserATM(UUID id_atm, RegisterAtmDTO atm) throws NotFoundException;
    AtmResponseDTO updateATM(UUID id_atm,AtmDTO atm) throws NotFoundException;
    void deleteATM(UUID id_atm) throws NotFoundException;
    AtmDTO getAtmById(UUID id_atm) throws NotFoundException;
    AtmDTO getAtmByName(String name) throws NotFoundException;
    List<AtmDTO> getAllATMs();
    void deleteATMByNameOrAliasOrDni(String name, String alias, String dni) throws NotFoundException;
}
