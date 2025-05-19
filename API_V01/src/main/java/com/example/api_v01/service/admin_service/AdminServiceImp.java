package com.example.api_v01.service.admin_service;

import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Admin;
import com.example.api_v01.repository.AdminRepository;
import com.example.api_v01.utils.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService , ExceptionMessage {

    private final AdminRepository adminRepository;

    //Solo se usa una vez para crear al admin
    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin findById(UUID id) throws NotFoundException {
        return adminRepository.findById(id).orElseThrow( () -> new NotFoundException(ADMIN_NOT_FOUND));
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

}
