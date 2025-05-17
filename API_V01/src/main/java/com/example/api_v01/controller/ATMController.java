package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.AtmDTO;
import com.example.api_v01.dto.response.AtmResponseDTO;
import com.example.api_v01.dto.response.RegisterAtmDTO;
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
public class ATMController {

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
    public ResponseEntity<?> saveATM(@PathVariable("adminId") UUID adminId, @RequestBody AtmDTO atmDTO) {
        try {
            AtmResponseDTO createdATM = atmservice.saveATM(adminId, atmDTO);
            SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                    .status(HttpStatus.CREATED)
                    .message("ATM creado exitosamente")
                    .data(createdATM)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<AtmResponseDTO>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PutMapping("/{atmId}/assign-user")
    public ResponseEntity<?> assignUserATM(@PathVariable("atmId") UUID atmId, @RequestBody RegisterAtmDTO registerATMDTO) {
        try {
            AtmResponseDTO updatedATM = atmservice.assingUserATM(atmId, registerATMDTO);
            SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                    .status(HttpStatus.OK)
                    .message("Usuario asignado exitosamente al ATM")
                    .data(updatedATM)
                    .build();
            return ResponseEntity.ok(successMessage);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<AtmResponseDTO>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PutMapping("/{atmId}")
    public ResponseEntity<?> updateATM(@PathVariable("atmId") UUID atmId, @RequestBody AtmDTO atmDTO) {
        try {
            AtmResponseDTO updatedATM = atmservice.updateATM(atmId, atmDTO);
            SuccessMessage<AtmResponseDTO> successMessage = SuccessMessage.<AtmResponseDTO>builder()
                    .status(HttpStatus.OK)
                    .message("ATM actualizado exitosamente")
                    .data(updatedATM)
                    .build();
            return ResponseEntity.ok(successMessage);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<AtmResponseDTO>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/{atmId}")
    public ResponseEntity<?> deleteATM(@PathVariable("atmId") UUID atmId) {
        try {
            atmservice.deleteATM(atmId);
            SuccessMessage<Void> successMessage = SuccessMessage.<Void>builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message("ATM eliminado exitosamente")
                    .build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<Void>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    //Para buscar y eliminar por nombre o el atm por nombre,apellido o dni
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteATMByNameOrAliasOrDni(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String alias,
            @RequestParam(required = false) String dni) throws NotFoundException {
        try {
            atmservice.deleteATMByNameOrAliasOrDni(name, alias, dni);
            SuccessMessage<Void> successMessage = SuccessMessage.<Void>builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message("ATM eliminado exitosamente")
                    .build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<Void>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }

    }

    @GetMapping("/{atmId}")
    public ResponseEntity<?> getAtmById(@PathVariable("atmId") UUID atmId) {
        try {
            AtmDTO atmDTO = atmservice.getAtmById(atmId);
            SuccessMessage<AtmDTO> successMessage = SuccessMessage.<AtmDTO>builder()
                    .status(HttpStatus.OK)
                    .message("ATM recuperado exitosamente")
                    .data(atmDTO)
                    .build();
            return ResponseEntity.ok(successMessage);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<AtmDTO>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessMessage<AtmDTO>> getAtmByName(@RequestParam String name) {
        try {
            AtmDTO atmDTO = atmservice.getAtmByName(name);
            SuccessMessage<AtmDTO> successMessage = SuccessMessage.<AtmDTO>builder()
                    .status(HttpStatus.OK)
                    .message("ATM recuperado exitosamente")
                    .data(atmDTO)
                    .build();
            return ResponseEntity.ok(successMessage);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    SuccessMessage.<AtmDTO>builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<SuccessMessage<List<AtmDTO>>> getAllATMs() {
        List<AtmDTO> atmList = atmservice.getAllATMs();
        SuccessMessage<List<AtmDTO>> successMessage = SuccessMessage.<List<AtmDTO>>builder()
                .status(HttpStatus.OK)
                .message("Todos los ATMs recuperados exitosamente")
                .data(atmList)
                .build();
        return ResponseEntity.ok(successMessage);
    }
}
