package com.example.api_v01.service.service_aux;

import com.example.api_v01.dto.response.ArchingResponseDTO;
import com.example.api_v01.handler.NotFoundException;

import java.util.UUID;

public interface ArchingProcessOrderSet {
    ArchingResponseDTO closeArching(UUID id_arching) throws NotFoundException;
}
