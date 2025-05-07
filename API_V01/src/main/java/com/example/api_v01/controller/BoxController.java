package com.example.api_v01.controller;

import com.example.api_v01.dto.BoxDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Box;
import com.example.api_v01.service.box_service.BoxService;
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

    @PostMapping("/{adminId}")
    public ResponseEntity<Box> saveBox(@PathVariable("adminId") UUID adminId, @RequestBody BoxDTO boxDTO){
        try {
            Box savedBox = boxservice.saveBox(adminId, boxDTO);
            return ResponseEntity.ok(savedBox);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{boxId}/toggle-status")
    public ResponseEntity<Box> toggleBoxStatus(@PathVariable("boxId") UUID boxId) {
        try {
            Box updatedBox = boxservice.toggleBoxStatus(boxId);
            return ResponseEntity.ok(updatedBox);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{boxId}/assign-atm/{atmId}")
    public ResponseEntity<Box> assignAtmToBox(@PathVariable("boxId") UUID boxId, @PathVariable("atmId") UUID atmId) {
        try {
            Box assignedBox = boxservice.assignAtmToBox(boxId, atmId);
            return ResponseEntity.ok(assignedBox);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{boxId}")
    public ResponseEntity<Box> getBoxInfo(@PathVariable("boxId") UUID boxId) {
        try {
            Box box = boxservice.getBoxInfo(boxId);
            return ResponseEntity.ok(box);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Box>> getBoxes() {
        List<Box> boxes = boxservice.getBoxes();
        return ResponseEntity.ok(boxes);
    }

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
