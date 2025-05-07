package com.example.api_v01.controller;


import com.example.api_v01.dto.ErrorMassage;
import com.example.api_v01.dto.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Admin;
import com.example.api_v01.repository.AdminRepository;
import com.example.api_v01.service.admin_service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminservice;

    @GetMapping("/{id_admin}")
    public ResponseEntity<?> getAdmin(@PathVariable UUID id_admin) throws NotFoundException {
        SuccessMessage<?> successMessage = SuccessMessage.builder()
                .status(HttpStatus.OK)
                .message("Se encontro el admin")
                .data(adminservice.findById(id_admin))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAdminList() {
        return ResponseEntity.ok(adminservice.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<?> ErrorFindAdmin() throws BadRequestException {
        throw new BadRequestException("Necesitad ingresar el id del admin para buscar");
    }
}
