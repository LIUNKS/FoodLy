package com.example.api_v01.service.arching_service;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;

import java.util.List;
import java.util.UUID;

public interface ArchingService {

    Arching saveArching(UUID id_box,ArchingDTO archingDTO) throws NotFoundException;
    Arching closeArching(UUID id_arching) throws NotFoundException;
    Arching updateArching(UUID id_arching, ArchingDTO archingDTO) throws NotFoundException;
    List<Arching> getAllArching();
    List<Arching> getAllArchingByATM(UUID id_atm) throws NotFoundException;
    Arching getArchingById(UUID id_arching) throws NotFoundException;

}