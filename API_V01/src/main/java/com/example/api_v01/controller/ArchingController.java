package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.dto.response.*;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.arching_service.ArchingService;
import com.example.api_v01.service.service_aux.ArchingProcessOrderSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("arching")
@RequiredArgsConstructor
public class ArchingController { //CONTROLADOR TESTEADO, LISTO PARA USAR


    private final ArchingService archingService;
    private final ArchingProcessOrderSet archingProcessOrderSet;

    // Retorna todos los Arching
    @GetMapping("/list")
    public ResponseEntity<?> getAllArching() {
        List<ArchingDTO> response = archingService.getAllArching();
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK, "Listado de Arching obtenido correctamente", response)
        );
    }

    // Traer Arching por su ID
    @GetMapping("/{id_arching}")
    public ResponseEntity<?> getArchingById(
            @PathVariable UUID id_arching
    ) throws NotFoundException {
        ArchingDTO response = archingService.getArchingDTOById(id_arching);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK, "Arching obtenido correctamente", response)
        );
    }

    // Traer Arching por el ID del ATM
    @GetMapping("/ATM/{id_atm}")
    public ResponseEntity<?> getArchingByATM(
            @PathVariable UUID id_atm
    ) throws NotFoundException {
        List<ArchingWithAtmDTO> response = archingService.getArchingByATM(id_atm);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK, "Listado de Arching por ATM obtenido correctamente", response)
        );
    }

    // Traer Arching por el nombre del ATM
    @GetMapping("/ATM/name/{name_atm}")
    public ResponseEntity<?> getArchingByNameATM(
            @PathVariable String name_atm
    ) throws NotFoundException {
        List<ArchingWithAtmDTO> response = archingService.getArchingByNameATM(name_atm);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK, "Listado de Arching por nombre de ATM obtenido correctamente", response)
        );
    }

    // Traer Arching por el ID del Box
    @GetMapping("/Box/{id_box}")
    public ResponseEntity<?> getArchingByBox(
            @PathVariable UUID id_box
    ) throws NotFoundException {
        List<ArchingWithBoxDTO> response = archingService.getArchingByBox(id_box);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK, "Listado de Arching por Box obtenido correctamente", response)
        );
    }

    // Traer Arching por el nombre del Box
    @GetMapping("/Box/name/{name_box}")
    public ResponseEntity<?> getArchingByNameBox(
            @PathVariable String name_box
    ) throws NotFoundException {
        List<ArchingWithBoxDTO> response = archingService.getArchingByNameBox(name_box);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK, "Listado de Arching por nombre de Box obtenido correctamente", response)
        );
    }

}
