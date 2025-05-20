package com.example.api_v01.service.arching_service;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.dto.response.ArchingInitDTO;
import com.example.api_v01.dto.response.ArchingResponseDTO;
import com.example.api_v01.dto.response.ArchingWithAtmDTO;
import com.example.api_v01.dto.response.ArchingWithBoxDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;
import com.example.api_v01.utils.Tuple;


import java.util.List;
import java.util.UUID;

public interface ArchingService {
    //Este no se usa
    Arching saveArching(Arching arching);
    Tuple<ArchingResponseDTO,UUID> saveArchingResponseDTO(UUID id_box, ArchingInitDTO archingInitDTO) throws NotFoundException, BadRequestException;
    List<ArchingDTO> getAllArching();
    ArchingDTO getArchingDTOById(UUID id_arching) throws NotFoundException;
    Arching getArchingById(UUID id_arching) throws NotFoundException;
    List<ArchingWithAtmDTO> getArchingByATM(UUID id_atm) throws NotFoundException;
    List<ArchingWithBoxDTO> getArchingByBox(UUID id_box) throws NotFoundException;
    List<ArchingWithAtmDTO> getArchingByNameATM(String name_ATM) throws NotFoundException;
    List<ArchingWithBoxDTO> getArchingByNameBox(String name_BOX) throws NotFoundException;
}