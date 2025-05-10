package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.BoxDTO;
import com.example.api_v01.dto.response.BoxResponseDTO;
import com.example.api_v01.dto.response.BoxWithAtmDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Box;
import com.example.api_v01.service.box_service.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("box")
public class BoxController {

    private final BoxService boxservice;

    public BoxController(BoxService boxservice) {
        this.boxservice = boxservice;
    }

    //Tareas
    //Agregarle URI a saveBox (Faltante)
    //Falta implementar los endponts con los servicios

    @PostMapping("/{id_admin}")
    public ResponseEntity<SuccessMessage> saveBox(@PathVariable("id_admin") UUID id_admin, @RequestBody BoxResponseDTO boxDTO) throws NotFoundException {
        SuccessMessage<BoxResponseDTO>successMessage = SuccessMessage.<BoxResponseDTO>builder()
                .status(HttpStatus.OK)
                .message("La data del box creado")
                .data(boxservice.saveBox(id_admin,boxDTO))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @PutMapping("/{id_box}/assign-atm/{id_atm}")
    public ResponseEntity<SuccessMessage> assignAtmToBox(@PathVariable("id_box") UUID id_box, @PathVariable("id_atm") UUID id_atm) throws NotFoundException {
        SuccessMessage<BoxWithAtmDTO>successMessage = SuccessMessage.<BoxWithAtmDTO>builder()
                .status(HttpStatus.OK)
                .message("La data del box creado:")
                .data(boxservice.assignAtmToBox(id_box,id_atm))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @GetMapping("/{id_box}")
    public ResponseEntity<SuccessMessage> getBoxInfo(@PathVariable("id_box") UUID id_box) throws NotFoundException {
        SuccessMessage<BoxDTO>successMessage = SuccessMessage.<BoxDTO>builder()
                .status(HttpStatus.OK)
                .message("La data del box creado:")
                .data(boxservice.getBoxInfo(id_box))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @GetMapping("/list")
    public ResponseEntity<SuccessMessage> getBoxes() {
        SuccessMessage<List<BoxDTO>>successMessage = SuccessMessage.<List<BoxDTO>>builder()
                .status(HttpStatus.OK)
                .message("La data del box creado:")
                .data(boxservice.getBoxes())
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @GetMapping("/by-atm/{id_atm}")
    public ResponseEntity<SuccessMessage> getBoxesByAtm(@PathVariable("id_atm") UUID id_atm) throws NotFoundException {
        SuccessMessage<List<BoxDTO>>successMessage = SuccessMessage.<List<BoxDTO>>builder()
                .status(HttpStatus.OK)
                .message("La data del box creado:")
                .data(boxservice.getBoxesByAtm(id_atm))
                .build();
        return ResponseEntity.ok().body(successMessage);
    }
}
