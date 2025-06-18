package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.BoxDTO;
import com.example.api_v01.dto.response.*;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.box_service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("box")
@RequiredArgsConstructor
public class BoxController { //CONTROLADOR LISTO PARA USAR

    private final BoxService boxservice;

    //Guarda una caja pasandole un dto,se necesita el id del admin que lo creara
    @PostMapping("/{id_admin}")
    public ResponseEntity<?> saveBox(@PathVariable("id_admin") UUID id_admin, @RequestBody BoxNameDTO boxDTO) throws NotFoundException {
        SuccessMessage<BoxResponseDTO>successMessage = SuccessMessage.<BoxResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("La data del box creado")
                .data(boxservice.saveBox(id_admin,boxDTO))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }


    //asigna un cajero a una caja,necesita el id del atm y box
    @PostMapping("/{id_box}/assign-atm/{id_atm}")
    public ResponseEntity<?> assignAtmToBox(@PathVariable("id_box") UUID id_box, @PathVariable("id_atm") UUID id_atm) throws NotFoundException, BadRequestException {
        SuccessMessage<BoxWithAtmDTO>successMessage = SuccessMessage.<BoxWithAtmDTO>builder()
                .status(HttpStatus.OK.value())
                .message("La data del box creado:")
                .data(boxservice.assignAtmToBox(id_box,id_atm))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //traer una caja por su id
    @GetMapping("/{id_box}")
    public ResponseEntity<?> getBoxInfo(@PathVariable("id_box") UUID id_box) throws NotFoundException {
        SuccessMessage<BoxDTO>successMessage = SuccessMessage.<BoxDTO>builder()
                .status(HttpStatus.OK.value())
                .message("La data del box creado:")
                .data(boxservice.getBoxInfo(id_box))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //sirve para cerrar una caja pero necesita el id de box y de la caja que estan actual
    @PostMapping("off-box/{id_box}")
    public ResponseEntity<?> OffBox(@PathVariable("id_box") UUID id_box) throws NotFoundException, BadRequestException {
        SuccessMessage<BoxWithArchingDTO>successMessage = SuccessMessage.<BoxWithArchingDTO>builder()
                .status(HttpStatus.OK.value())
                .message("La caja se ha cerrado")
                .data(boxservice.toggleBoxDeactivationStatus(id_box))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //habre una caja y crea un arqueo pasandole su dto inicial,me devuelve el id del arqueo para la logica
    @PostMapping("on-box/{id_box}")
    public ResponseEntity<?> OnBox(@PathVariable("id_box") UUID id_box,@RequestBody ArchingInitDTO archingInitDTO) throws NotFoundException, BadRequestException {
        SuccessMessage<BoxResponseWithArchingDTO>successMessage = SuccessMessage.<BoxResponseWithArchingDTO>builder()
                .status(HttpStatus.OK.value())
                .message("La caja se ha abierto")
                .data(boxservice.toggleBoxActiveStatus(id_box,archingInitDTO))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //me da una lista de las cajas
    @GetMapping("/list")
    public ResponseEntity<?> getBoxes() {
        SuccessMessage<List<BoxDTO>>successMessage = SuccessMessage.<List<BoxDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("La data del box creado:")
                .data(boxservice.getBoxes())
                .build();
        return ResponseEntity.ok().body(successMessage);
    }


    //me busca la lista de cajas que esta asignadas a un determinado cajero,busca por el id del cajero
    @GetMapping("/by-atm/{id_atm}")
    public ResponseEntity<?> getBoxesByAtm(@PathVariable("id_atm") UUID id_atm) throws NotFoundException {
        SuccessMessage<List<BoxDTO>>successMessage = SuccessMessage.<List<BoxDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("La data del box creado:")
                .data(boxservice.getBoxesByAtm(id_atm))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }


}
