package com.example.api_v01.controller;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.dto.RegisterATMDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;
import com.example.api_v01.service.atm_service.ATMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/atm")
public class ATMController {

    private final ATMService atmservice;
    public ATMController(ATMService atmservice) {
        this.atmservice = atmservice;
    }

    @Operation(
            summary = "Crea un nuevo ATM",
            description = "Este endpoint permite crear un nuevo ATM asociado a un administrador específico mediante el ID del administrador."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ATM creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATM.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Administrador no encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{adminId}")
    public ResponseEntity<ATM> saveATM(@PathVariable("adminId") UUID adminId, @RequestBody AtmDTO atmDTO) {
        try {
            ATM createdATM = atmservice.saveATM(adminId, atmDTO);
            return ResponseEntity.ok(createdATM);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Asigna un usuario a un ATM",
            description = "Asigna un usuario a un ATM específico mediante el ID del ATM."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario asignado al ATM exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATM.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ATM no encontrado",
                    content = @Content
            )
    })

    @PutMapping("/{atmId}/assign-user")
    public ResponseEntity<ATM> assignUserATM(@PathVariable("atmId") UUID atmId, @RequestBody RegisterATMDTO registerATMDTO) {
        try {
            ATM updatedATM = atmservice.assingUserATM(atmId, registerATMDTO);
            return ResponseEntity.ok(updatedATM);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Actualiza un ATM",
            description = "Permite actualizar la información de un ATM específico mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ATM actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATM.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ATM no encontrado",
                    content = @Content
            )
    })

    @PutMapping("/{atmId}")
    public ResponseEntity<ATM> updateATM(@PathVariable("atmId") UUID atmId, @RequestBody AtmDTO atmDTO) {
        try {
            ATM updatedATM = atmservice.updateATM(atmId, atmDTO);
            return ResponseEntity.ok(updatedATM);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Elimina un ATM",
            description = "Este endpoint elimina un ATM específico según su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "ATM eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ATM no encontrado",
                    content = @Content
            )
    })

    @DeleteMapping("/{atmId}")
    public ResponseEntity<Void> deleteATM(@PathVariable("atmId") UUID atmId) {
        try {
            atmservice.deleteATM(atmId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtiene un ATM por ID",
            description = "Devuelve la información de un ATM específico según el ID proporcionado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATM.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ATM no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/{atmId}")
    public ResponseEntity<ATM> getAtmById(@PathVariable("atmId") UUID atmId) {
        try {
            ATM atm = atmservice.getAtmById(atmId);
            return ResponseEntity.ok(atm);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtiene la lista de todos los ATMs",
            description = "Este endpoint devuelve todos los ATMs disponibles en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATM.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<ATM>> getAllATMs() {
        List<ATM> atmList = atmservice.getAllATMs();
        return ResponseEntity.ok(atmList);
    }


}
