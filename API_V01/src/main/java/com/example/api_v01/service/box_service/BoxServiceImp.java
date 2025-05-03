package com.example.api_v01.service.box_service;

import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.Box;
import com.example.api_v01.repository.ATMRepository;
import com.example.api_v01.repository.AdminRepository;
import com.example.api_v01.repository.BoxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BoxServiceImp {
    private final BoxRepository boxRepository;
    private final AdminRepository adminRepository;
    private final ATMRepository atmRepository;

    public BoxServiceImp(BoxRepository boxRepository, AdminRepository adminRepository, ATMRepository atmRepository) {
        this.boxRepository = boxRepository;
        this.adminRepository = adminRepository;
        this.atmRepository = atmRepository;
    }

    //Opcional si realmente se usaria
    @Transactional
    public Box createBox(String name, UUID adminId) throws NotFoundException {
        Box box = new Box();
        box.setName_box(name);
        box.setDate(LocalDate.now());
        box.setIs_open(false);

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("Admin no encontrado"));
        box.setAdmin(admin);

        return boxRepository.save(box);
    }

    // Cambiar estado de la caja (abrir/cerrar)
    @Transactional
    public Box toggleBoxStatus(UUID boxId, boolean newStatus) throws NotFoundException{
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new NotFoundException("Caja no encontrada"));
        box.setIs_open(newStatus);
        return boxRepository.save(box);
    }

    // Asignar un ATM a la caja
    @Transactional
    public Box assignAtmToBox(UUID boxId, UUID atmId) throws NotFoundException{
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new NotFoundException("Caja no encontrada"));
        ATM atm = atmRepository.findById(atmId)
                .orElseThrow(() -> new NotFoundException("ATM no encontrado"));
        box.setAtm(atm);
        return boxRepository.save(box);
    }

    // Obtener el estado actual de la caja
    public boolean getBoxStatus(UUID boxId) throws NotFoundException{
        return boxRepository.findById(boxId)
                .map(Box::getIs_open)
                .orElseThrow(() -> new NotFoundException("Caja no encontrada"));
    }

    // Obtener información básica de la caja
    public Box getBoxInfo(UUID boxId) throws NotFoundException{
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new NotFoundException("Caja no encontrada"));
    }
}
