package com.example.api_v01.controller;

import com.example.api_v01.dto.response.AuthResponse;
import com.example.api_v01.dto.response.LoginDTO;

import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.user_service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    public final AuthService authService;

    //Me va pedir el user y password de un usuario para identificarlo
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws NotFoundException {
        AuthResponse authResponse = authService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok(authResponse);
    }

}
