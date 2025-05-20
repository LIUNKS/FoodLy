package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.AtmDTO;
import com.example.api_v01.dto.response.AtmResponseDTO;
import com.example.api_v01.dto.response.RegisterAtmUserDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.atm_service.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("atm")
@RequiredArgsConstructor
public class ATMController { //CONTROLADOR TESTEADO, LISTO PARA USAR

    private final ATMService atmservice;

    //Recibe un DTO para registrar los datos del atm,se necesita el id del admin para guardar al atm
    @PostMapping("/{adminId}")
    public ResponseEntity<?> saveATM(@PathVariable("adminId") UUID adminId, @RequestBody AtmResponseDTO atmDTO) throws NotFoundException {
        AtmResponseDTO createdATM = atmservice.saveATM(adminId, atmDTO);
        SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("ATM creado exitosamente")
                .data(createdATM)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    //Recibe un DTO para registrar el user de un atm ,se necesita el id del atm para asignarle el user
    @PostMapping("/{atmId}/assign-user")
    public ResponseEntity<?> assignUserATM(@PathVariable("atmId") UUID atmId, @RequestBody RegisterAtmUserDTO registerATMDTOUser) throws NotFoundException {
        AtmResponseDTO updatedATM = atmservice.assingUserATM(atmId, registerATMDTOUser);
        SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Usuario asignado exitosamente al ATM")
                .data(updatedATM)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Me devuelve la lista de ATMs
    @GetMapping("/list")
    public ResponseEntity<?> getAllATMs() {
        List<AtmDTO> atmList = atmservice.getAllATMs();
        SuccessMessage<List<AtmDTO>> successMessage = SuccessMessage.<List<AtmDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Todos los ATMs recuperados exitosamente")
                .data(atmList)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Me devuelve un ATM por su id
    @GetMapping("/{atmId}")
    public ResponseEntity<?> getAtmById(@PathVariable("atmId") UUID atmId) throws NotFoundException {
        AtmDTO atmDTO = atmservice.getAtmById(atmId);
        SuccessMessage<AtmDTO> successMessage = SuccessMessage.<AtmDTO>builder()
                .status(HttpStatus.OK.value())
                .message("ATM recuperado exitosamente")
                .data(atmDTO)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Me devulve un ATM por su nombre
    @GetMapping("/searchByName")
    public ResponseEntity<?> getAtmByName(@RequestParam String name) throws NotFoundException {
        AtmDTO atmDTO = atmservice.getAtmByName(name);
        SuccessMessage<AtmDTO> successMessage = SuccessMessage.<AtmDTO>builder()
                .status(HttpStatus.OK.value())
                .message("ATM recuperado exitosamente")
                .data(atmDTO)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Eliminar el ATM y su user
    @DeleteMapping("/{atmId}")
    public ResponseEntity<?> deleteATM(@PathVariable("atmId") UUID atmId) throws NotFoundException {
        atmservice.deleteATM(atmId);
        SuccessMessage<Void> successMessage = SuccessMessage.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("ATM eliminado exitosamente")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
    }

    //Actualiza un ATM pasandole un DTO con los datos actualizar ,necesita el id del atm
    @PatchMapping("/{atmId}")
    public ResponseEntity<?> updateATM(@PathVariable("atmId") UUID atmId, @RequestBody AtmResponseDTO atmDTO) throws NotFoundException {
        AtmResponseDTO updatedATM = atmservice.updateATM(atmId, atmDTO);
        SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("ATM actualizado exitosamente")
                .data(updatedATM)
                .build();
        return ResponseEntity.ok(successMessage);
    }

}
