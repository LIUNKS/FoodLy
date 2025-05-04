package com.example.api_v01.service.atm_service;

import com.example.api_v01.dto.AtmDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;
import com.example.api_v01.repository.ATMRepository;
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

    @Override
    public ATM saveATM(AtmDTO atm) {
        ATM newATM = ATMMovement.saveATM(atm);
        return atmRepository.save(newATM);
    }

    @Override
    public ATM updateATM(UUID id_atm, AtmDTO atm) throws NotFoundException {
        Optional<ATM>ATMOptional = atmRepository.findById(id_atm);
        if (!ATMOptional.isPresent()) {
            throw new NotFoundException(ATM_NOT_FOUND);
        }
        ATM ATM = validateATM(ATMOptional.get(), atm);
        return atmRepository.save(ATM);
    }

    @Override
    public void deleteATM(UUID id_atm) throws NotFoundException {
        Optional<ATM>ATMOptional = atmRepository.findById(id_atm);
        if(!ATMOptional.isPresent()){
            throw new NotFoundException(ATM_NOT_FOUND);
        }
        atmRepository.delete(ATMOptional.get());
    }

    @Override
    public ATM getAtmById(UUID id_atm) throws NotFoundException {
        return atmRepository.findById(id_atm)
                .orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND));
    }

    @Override
    public List<ATM> getAllATMs() {
        return atmRepository.findAll();
    }

    private ATM validateATM(ATM atm,AtmDTO atmDTO) {
        if(atmDTO.getName_atm() != null){
            atm.setName_atm(atmDTO.getName_atm());
        }
        if(atm.getDate() != null){
            atm.setDate(atm.getDate());
        }
        if(atm.getAlias() != null){
            atm.setAlias(atm.getAlias());
        }
        if(atm.getEmail() != null){
            atm.setEmail(atm.getEmail());
        }
        if(atm.getPhone() != null){
            atm.setPhone(atm.getPhone());
        }
        if(atm.getDni() != null){
            atm.setDni(atm.getDni());
        }
        if(atm.getUser_atm() != null){
            atm.setUser_atm(atm.getUser_atm());
        }
        return atm;
    }

}
