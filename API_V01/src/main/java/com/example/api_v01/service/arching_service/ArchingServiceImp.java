package com.example.api_v01.service.arching_service;

import com.example.api_v01.dto.ArchingDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.Box;
import com.example.api_v01.repository.ATMRepository;
import com.example.api_v01.repository.ArchingRepository;
import com.example.api_v01.repository.BoxRepository;
import com.example.api_v01.service.box_service.BoxService;
import com.example.api_v01.utils.ArchingMovement;
import com.example.api_v01.utils.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArchingServiceImp implements ArchingService, ExceptionMessage {

    private final ArchingRepository archingRepository;

    private final ATMRepository atmRepository;

    private final BoxRepository boxRepository;

    private final BoxService boxService;

    @Override
    public Arching saveArching(UUID id_box,ArchingDTO archingDTO) throws NotFoundException {
        Box box = boxRepository.findById(id_box)
                .orElseThrow( () -> new NotFoundException(BOX_NOT_FOUND));
        Arching arching = ArchingMovement.CreateArching(box,archingDTO);
        return archingRepository.save(arching);
    }

    @Override
    public Arching closeArching(UUID id_box, UUID id_arching) throws NotFoundException {

        Box box = boxService.toggleBoxStatus(id_box); //Este se guarda ya en la bd en su mismo metodo

        Arching arching = archingRepository.findById(id_arching)
                .orElseThrow( () -> new NotFoundException(ARCHING_NOT_FOUND));

        Arching archingClose = ArchingMovement.CloseArchingBox(arching,null); //Falta implementar el Monto Final, Ya te calcula el final

        return archingRepository.save(archingClose);
    }














    @Override
    public Arching updateArching(UUID id_arching, ArchingDTO archingDTO) throws NotFoundException {
        return null;
    }

    @Override
    public List<Arching> getAllArching() {
        return archingRepository.findAll();
    }

    @Override
    public List<Arching> getAllArchingByATM(UUID id_atm) throws NotFoundException {
        if(!atmRepository.existsById(id_atm)){
            throw new NotFoundException(ATM_NOT_FOUND);
        }
        return archingRepository.findArchingByATM(id_atm)
                .orElseThrow( () -> new NotFoundException(ATM_ARCHING_NOT_FOUND));

    }

    @Override
    public Arching getArchingById(UUID id_arching) throws NotFoundException {
        return archingRepository.findById(id_arching)
                .orElseThrow(()-> new NotFoundException(ARCHING_NOT_FOUND));
    }
}
