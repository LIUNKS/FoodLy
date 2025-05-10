package com.example.api_v01.service.arching_service;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.dto.response.ArchingInitDTO;
import com.example.api_v01.dto.response.ArchingResponseDTO;
import com.example.api_v01.dto.response.ArchingWithAtmDTO;
import com.example.api_v01.dto.response.ArchingWithBoxDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;


import java.util.List;
import java.util.UUID;

public interface ArchingService {

    Arching saveArching(Arching arching);
    ArchingResponseDTO saveArchingResponseDTO(UUID id_box, ArchingInitDTO archingInitDTO) throws NotFoundException;
    List<ArchingDTO> getAllArching();
    ArchingDTO getArchingDTOById(UUID id_arching) throws NotFoundException;
    Arching getArchingById(UUID id_arching) throws NotFoundException;
    List<ArchingWithAtmDTO> getArchingByATM(UUID id_atm) throws NotFoundException;
    List<ArchingWithBoxDTO> getArchingByBox(UUID id_box) throws NotFoundException;
    List<ArchingWithAtmDTO> getArchingByNameATM(String name_ATM) throws NotFoundException;
    List<ArchingWithBoxDTO> getArchingByNameBox(String name_BOX) throws NotFoundException;


    //No se sabe si se implementara
    ArchingResponseDTO updateArching(UUID id_arching, ArchingInitDTO archingInitDTO) throws NotFoundException;
}