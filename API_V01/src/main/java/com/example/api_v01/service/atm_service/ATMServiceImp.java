package com.example.api_v01.service.atm_service;

import com.example.api_v01.dto.entityLike.AtmDTO;
import com.example.api_v01.dto.response.AtmResponseDTO;
import com.example.api_v01.dto.response.RegisterAtmDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;
import com.example.api_v01.model.Admin;
import com.example.api_v01.repository.ATMRepository;
import com.example.api_v01.service.admin_service.AdminService;
import com.example.api_v01.utils.ATMMovement;
import com.example.api_v01.utils.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ATMServiceImp implements ATMService, ExceptionMessage {

    private final ATMRepository atmRepository;
    private final AdminService adminService;

    @Override
    public AtmResponseDTO saveATM(UUID id_admin, AtmDTO atm) throws NotFoundException {
        Admin admin = adminService.findById(id_admin);
        ATM newATM = ATMMovement.saveATM(atm,admin);
        ATM savedATM = atmRepository.save(newATM);
        return ATMMovement.convertToResponseDTO(savedATM);
    }

    @Override
    public AtmResponseDTO assingUserATM(UUID id_atm, RegisterAtmDTO atm) throws NotFoundException {
        ATM atmOptional = atmRepository.findById(id_atm)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.ATM_NOT_FOUND));
        ATM AtmUser = ATMMovement.AssignUser(atmOptional,atm);
        ATM savedATM = atmRepository.save(AtmUser);
        return ATMMovement.convertToResponseDTO(savedATM); //transform
    }

    @Override
    public AtmResponseDTO updateATM(UUID id_atm, AtmDTO atm) throws NotFoundException {
        Optional<ATM>ATMOptional = atmRepository.findById(id_atm);
        if (!ATMOptional.isPresent()) {
            throw new NotFoundException(ATM_NOT_FOUND);
        }
        ATM ATM = ATMMovement.validateATM(ATMOptional.get(), atm);
        ATM savedATM = atmRepository.save(ATM);
        return ATMMovement.convertToResponseDTO(savedATM); //transform
    }

    @Override
    public void deleteATM(UUID id_atm) throws NotFoundException {

        Optional<ATM>ATMOptional = atmRepository.findById(id_atm);

        if(!ATMOptional.isPresent()){
            throw new NotFoundException(ATM_NOT_FOUND);
        }

        ATMOptional.get().setAdmin(null);

        atmRepository.delete(ATMOptional.get());
    }

    //Implementar para buscar el atm por nombre,apellido o dni para eliminarlo
    @Override
    public void deleteATMByNameOrAliasOrDni(String name, String alias, String dni) throws NotFoundException {
        ATM atm = atmRepository.findByNameAtmOrAliasOrDni(name, alias, dni)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.ATM_NOT_FOUND));

        // quitar admin (en caso de ser necesario no)
        atm.setAdmin(null);

        // eliminar atm
        atmRepository.delete(atm);

    }

    @Override
    public AtmDTO getAtmById(UUID id_atm) throws NotFoundException {
        ATM atm = atmRepository.findById(id_atm)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.ATM_NOT_FOUND));
        return ATMMovement.convertToDTO(atm);
    }

    @Override
    public AtmDTO getAtmByName(String name) throws NotFoundException {
        ATM atm = atmRepository.findByNameAtm(name)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.ATM_NOT_FOUND));
        return ATMMovement.convertToDTO(atm);
    }

    @Override
    public List<AtmDTO> getAllATMs() {
        List<ATM> atms = atmRepository.findAll();
        return atms.stream()
                .map(ATMMovement::convertToDTO) //transform c/u ATM a AtmDTO
                .toList();
    }

}
