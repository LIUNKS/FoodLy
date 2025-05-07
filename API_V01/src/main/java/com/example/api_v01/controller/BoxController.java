package com.example.api_v01.controller;

import com.example.api_v01.dto.BoxDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Box;
import com.example.api_v01.service.box_service.BoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/box")
public class BoxController {

    private final BoxService boxservice;

    public BoxController(BoxService boxservice) {
        this.boxservice = boxservice;
    }

    @Operation(
            summary = "Crea una nueva caja (Box)",
            description = "Este endpoint permite crear una nueva caja asociada a un administrador específico mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Caja creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Box.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Administrador no encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{adminId}")
    public ResponseEntity<Box> saveBox(@PathVariable("adminId") UUID adminId, @RequestBody BoxDTO boxDTO){
        try {
            Box savedBox = boxservice.saveBox(adminId, boxDTO);
            return ResponseEntity.ok(savedBox);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Cambia el estado de una caja",
            description = "Este endpoint alterna el estado de una caja (abierta o cerrada) basado en su estado actual, identificada por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado de la caja actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Box.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Caja no encontrada",
                    content = @Content
            )
    })
    @PutMapping("/{boxId}/toggle-status")
    public ResponseEntity<Box> toggleBoxStatus(@PathVariable("boxId") UUID boxId) {
        try {
            Box updatedBox = boxservice.toggleBoxStatus(boxId);
            return ResponseEntity.ok(updatedBox);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Asigna un ATM a una caja",
            description = "Este endpoint permite asignar un cajero automático (ATM) a una caja específica mediante sus IDs respectivos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ATM asignado a la caja exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Box.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Caja o ATM no encontrados",
                    content = @Content
            )
    })
    @PutMapping("/{boxId}/assign-atm/{atmId}")
    public ResponseEntity<Box> assignAtmToBox(@PathVariable("boxId") UUID boxId, @PathVariable("atmId") UUID atmId) {
        try {
            Box assignedBox = boxservice.assignAtmToBox(boxId, atmId);
            return ResponseEntity.ok(assignedBox);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtiene información de una caja",
            description = "Este endpoint devuelve los detalles de una caja específica, identificada por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Box.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Caja no encontrada",
                    content = @Content
            )
    })
    @GetMapping("/{boxId}")
    public ResponseEntity<Box> getBoxInfo(@PathVariable("boxId") UUID boxId) {
        try {
            Box box = boxservice.getBoxInfo(boxId);
            return ResponseEntity.ok(box);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtiene todas las cajas",
            description = "Este endpoint devuelve una lista de todas las cajas disponibles en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Box.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<Box>> getBoxes() {
        List<Box> boxes = boxservice.getBoxes();
        return ResponseEntity.ok(boxes);
    }

    @Operation(
            summary = "Obtiene cajas asociadas a un ATM",
            description = "Devuelve una lista de cajas relacionadas con un ATM específico identificado por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Box.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ATM no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/by-atm/{atmId}")
    public ResponseEntity<List<Box>> getBoxesByAtm(@PathVariable("atmId") UUID atmId) {
        try {
            List<Box> boxes = boxservice.getBoxesByAtm(atmId);
            return ResponseEntity.ok(boxes);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
