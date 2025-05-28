package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.dto.response.*;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.arching_service.ArchingService;
import com.example.api_v01.service.service_aux.ArchingProcessOrderSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    // Retorna todos los Arching
    @GetMapping("/list")
    public ResponseEntity<?> getAllArching(@RequestParam int page) {
        List<ArchingDTO> response = archingService.getAllArching(page);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK.value(), "Listado de Arching obtenido correctamente", response)
        );
    }

    // Traer Arching por su ID
    @GetMapping("/{id_arching}")
    public ResponseEntity<?> getArchingById(
            @PathVariable UUID id_arching
    ) throws NotFoundException {
        ArchingDTO response = archingService.getArchingDTOById(id_arching);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK.value(), "Arching obtenido correctamente", response)
        );
    }

    // Traer Arching por el ID del ATM
    @GetMapping("/ATM/{id_atm}")
    public ResponseEntity<?> getArchingByATM(
            @PathVariable UUID id_atm,
            @RequestParam int page
    ) throws NotFoundException {
        List<ArchingWithAtmDTO> response = archingService.getArchingByATM(id_atm,page);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK.value(), "Listado de Arching por ATM obtenido correctamente", response)
        );
    }

    // Traer Arching por el nombre del ATM
    @GetMapping("/ATM/name/{name_atm}")
    public ResponseEntity<?> getArchingByNameATM(
            @PathVariable String name_atm,
            @RequestParam int page
    ) throws NotFoundException {
        List<ArchingWithAtmDTO> response = archingService.getArchingByNameATM(name_atm,page);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK.value(), "Listado de Arching por nombre de ATM obtenido correctamente", response)
        );
    }

    // Traer Arching por el ID del Box
    @GetMapping("/Box/{id_box}")
    public ResponseEntity<?> getArchingByBox(
            @PathVariable UUID id_box,
            @RequestParam int page
    ) throws NotFoundException {
        List<ArchingWithBoxDTO> response = archingService.getArchingByBox(id_box,page);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK.value(), "Listado de Arching por Box obtenido correctamente", response)
        );
    }

    // Traer Arching por el nombre del Box
    @GetMapping("/Box/name/{name_box}")
    public ResponseEntity<?> getArchingByNameBox(
            @PathVariable String name_box,
            @RequestParam int page
    ) throws NotFoundException {
        List<ArchingWithBoxDTO> response = archingService.getArchingByNameBox(name_box,page);
        return ResponseEntity.ok(
                new SuccessMessage<>(HttpStatus.OK.value(), "Listado de Arching por nombre de Box obtenido correctamente", response)
        );
    }

}
