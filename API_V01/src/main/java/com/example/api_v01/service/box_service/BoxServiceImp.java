package com.example.api_v01.service.box_service;

import com.example.api_v01.dto.BoxDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ATM;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.Box;
import com.example.api_v01.repository.ATMRepository;
import com.example.api_v01.repository.AdminRepository;
import com.example.api_v01.repository.BoxRepository;
import com.example.api_v01.utils.BoxMovement;
import com.example.api_v01.utils.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoxServiceImp implements BoxService, ExceptionMessage {

    private final BoxRepository boxRepository;
    private final ATMRepository atmRepository;

    @Override
    public Box saveBox(BoxDTO box) {
        Box newBox = BoxMovement.CreateBox(box);
        return boxRepository.save(newBox);
    }

    @Override
    public Box toggleBoxStatus(UUID id_box) throws NotFoundException {
        Optional<Box> BoxOptional = boxRepository.findById(id_box);

        if(!BoxOptional.isPresent()) {
            throw new NotFoundException(BOX_NOT_FOUND);
        }
        if(BoxOptional.get().getIs_open() == true) {
            BoxOptional.get().setIs_open(false);
        }
        if(BoxOptional.get().getIs_open() == false) {
            BoxOptional.get().setIs_open(true);
        }
        return boxRepository.save(BoxOptional.get());
    }

    @Override
    public Box assignAtmToBox(UUID id_box, UUID id_atm) throws NotFoundException {
        ATM atm = atmRepository.findById(id_atm)
                .orElseThrow( () -> new NotFoundException(ATM_NOT_FOUND));
        Box box = boxRepository.findById(id_box)
                .orElseThrow( () -> new NotFoundException(BOX_NOT_FOUND));
        box.setAtm(atm);
        return boxRepository.save(box);
    }

    @Override
    public Box getBoxInfo(UUID boxId) throws NotFoundException {
        return boxRepository.findById(boxId)
                .orElseThrow( () -> new NotFoundException(BOX_NOT_FOUND) );
    }

    @Override
    public List<Box> getBoxes() {
        return boxRepository.findAll();
    }

    @Override
    public List<Box> getBoxesByAtm(UUID id_atm) {
        return boxRepository.BoxByATM(id_atm);
    }
}
