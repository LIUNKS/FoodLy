package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.AtmDTO;
import com.example.api_v01.dto.response.AtmResponseDTO;
import com.example.api_v01.dto.response.RegisterAtmUserDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.atm_service.ATMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("atm")
public class ATMController { //CONTROLADOR TESTEADO, LISTO PARA USAR

    private final ATMService atmservice;

    public ATMController(ATMService atmservice) {
        this.atmservice = atmservice;
    }

    //Tareas:
    //Cambiar la sintaxis del controlador usando SuccessMessage
    //En los Servicios de ATM:
    //Los que guardar,cambiar o actualizan tiene que devolver un AtmResponseDTO = No trae el Id (saveATM,assignUserATM,updateATM)
    //Los que son de busqueda o Traen un lista tienen que Devolver un AtmDTO = Trae el Id (getAtmById,getAllATMs)
    //Tambien añadir un nuevo endponit para buscar al Atm por nombre (Debe devolve un AtmDTO)
    //Agregarle URI a saveATM (Faltante) para sabe donde esta ubicado (Esto hazlo despues de haber cambiado la sintaxis)
    //(Usar UriGenric es un utils del proyecto propio)

    @PostMapping("/{adminId}")
    public ResponseEntity<?> saveATM(@PathVariable("adminId") UUID adminId, @RequestBody AtmResponseDTO atmDTO) throws NotFoundException {
        AtmResponseDTO createdATM = atmservice.saveATM(adminId, atmDTO);
        SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                .status(HttpStatus.CREATED)
                .message("ATM creado exitosamente")
                .data(createdATM)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @PostMapping("/{atmId}/assign-user")
    public ResponseEntity<?> assignUserATM(@PathVariable("atmId") UUID atmId, @RequestBody RegisterAtmUserDTO registerATMDTOUser) throws NotFoundException {
        AtmResponseDTO updatedATM = atmservice.assingUserATM(atmId, registerATMDTOUser);
        SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                .status(HttpStatus.OK)
                .message("Usuario asignado exitosamente al ATM")
                .data(updatedATM)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllATMs() {
        List<AtmDTO> atmList = atmservice.getAllATMs();
        SuccessMessage<List<AtmDTO>> successMessage = SuccessMessage.<List<AtmDTO>>builder()
                .status(HttpStatus.OK)
                .message("Todos los ATMs recuperados exitosamente")
                .data(atmList)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/{atmId}")
    public ResponseEntity<?> getAtmById(@PathVariable("atmId") UUID atmId) throws NotFoundException {
        AtmDTO atmDTO = atmservice.getAtmById(atmId);
        SuccessMessage<AtmDTO> successMessage = SuccessMessage.<AtmDTO>builder()
                .status(HttpStatus.OK)
                .message("ATM recuperado exitosamente")
                .data(atmDTO)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> getAtmByName(@RequestParam String name) throws NotFoundException {
        AtmDTO atmDTO = atmservice.getAtmByName(name);
        SuccessMessage<AtmDTO> successMessage = SuccessMessage.<AtmDTO>builder()
                .status(HttpStatus.OK)
                .message("ATM recuperado exitosamente")
                .data(atmDTO)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @DeleteMapping("/{atmId}")
    public ResponseEntity<?> deleteATM(@PathVariable("atmId") UUID atmId) throws NotFoundException {
        atmservice.deleteATM(atmId);
        SuccessMessage<Void> successMessage = SuccessMessage.<Void>builder()
                .status(HttpStatus.NO_CONTENT)
                .message("ATM eliminado exitosamente")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
    }


    @PatchMapping("/{atmId}")
    public ResponseEntity<?> updateATM(@PathVariable("atmId") UUID atmId, @RequestBody AtmResponseDTO atmDTO) throws NotFoundException {
        AtmResponseDTO updatedATM = atmservice.updateATM(atmId, atmDTO);
        SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                .status(HttpStatus.OK)
                .message("ATM actualizado exitosamente")
                .data(updatedATM)
                .build();
        return ResponseEntity.ok(successMessage);
    }

}
