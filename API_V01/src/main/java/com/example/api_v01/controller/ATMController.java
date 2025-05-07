package com.example.api_v01.controller;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.dto.RegisterATMDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;
import com.example.api_v01.service.atm_service.ATMService;
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

    @PostMapping("/{adminId}")
    public ResponseEntity<ATM> saveATM(@PathVariable("adminId") UUID adminId, @RequestBody AtmDTO atmDTO) {
        try {
            ATM createdATM = atmservice.saveATM(adminId, atmDTO);
            return ResponseEntity.ok(createdATM);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{atmId}/assign-user")
    public ResponseEntity<ATM> assignUserATM(@PathVariable("atmId") UUID atmId, @RequestBody RegisterATMDTO registerATMDTO) {
        try {
            ATM updatedATM = atmservice.assingUserATM(atmId, registerATMDTO);
            return ResponseEntity.ok(updatedATM);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{atmId}")
    public ResponseEntity<ATM> updateATM(@PathVariable("atmId") UUID atmId, @RequestBody AtmDTO atmDTO) {
        try {
            ATM updatedATM = atmservice.updateATM(atmId, atmDTO);
            return ResponseEntity.ok(updatedATM);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{atmId}")
    public ResponseEntity<Void> deleteATM(@PathVariable("atmId") UUID atmId) {
        try {
            atmservice.deleteATM(atmId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{atmId}")
    public ResponseEntity<ATM> getAtmById(@PathVariable("atmId") UUID atmId) {
        try {
            ATM atm = atmservice.getAtmById(atmId);
            return ResponseEntity.ok(atm);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ATM>> getAllATMs() {
        List<ATM> atmList = atmservice.getAllATMs();
        return ResponseEntity.ok(atmList);
    }


}
