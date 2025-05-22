package com.example.api_v01.service.arching_service;

import com.example.api_v01.dto.entityLike.ArchingDTO;
import com.example.api_v01.dto.response.ArchingInitDTO;
import com.example.api_v01.dto.response.ArchingResponseDTO;
import com.example.api_v01.dto.response.ArchingWithAtmDTO;
import com.example.api_v01.dto.response.ArchingWithBoxDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.Box;
import com.example.api_v01.repository.ArchingRepository;
import com.example.api_v01.repository.BoxRepository;
import com.example.api_v01.service.box_service.BoxService;
import com.example.api_v01.utils.ArchingMovement;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArchingServiceImp implements ArchingService, ExceptionMessage {

    private final ArchingRepository archingRepository;

    private final BoxRepository boxRepository;

    //Se usa en un servicio auxiliar para la logica no borrar
    @Override
    public Arching saveArching(Arching arching) {
        return archingRepository.save(arching);
    }

    //Lo utiliza otro servicio,!!! NO BORRAR
    @Override
    public Arching getArchingById(UUID id_arching) throws NotFoundException {
        return archingRepository.findById(id_arching)
                .orElseThrow(()-> new NotFoundException(ARCHING_NOT_FOUND));
    }






    //Servicio para guardar el arqueo
    @Override
    public Tuple<ArchingResponseDTO,UUID> saveArchingResponseDTO(UUID id_box, ArchingInitDTO archingInitDTO) throws NotFoundException, BadRequestException {
        Box box = boxRepository.findById(id_box).orElseThrow(()-> new NotFoundException(BOX_NOT_FOUND));
        if(!box.getIs_open()){
            throw new BadRequestException("La caja debe estar abierta antes de asignarle un arqueo");
        }
        Arching arching = archingRepository.save(ArchingMovement.CreateArchingInit(box,archingInitDTO)) ;
        return new Tuple<>(ArchingMovement.CreateArchingResponseDTO(arching),arching.getId_arching());
    }





    //Me trae todos los arqueos
    @Override
    public List<ArchingDTO> getAllArching() {
        return ArchingMovement.CreateListArchingDTO(archingRepository.findAll());
    }

    //Me traen el arqueo por su id
    @Override
    public ArchingDTO getArchingDTOById(UUID id_arching) throws NotFoundException {
        return ArchingMovement
                .TransformArchingDTO(
                        archingRepository.findById(id_arching)
                                .orElseThrow( () -> new NotFoundException(ExceptionMessage.ARCHING_NOT_FOUND) )
                );
    }

    //Me traer todos los arqueos por el id del ATM
    @Override
    public List<ArchingWithAtmDTO> getArchingByATM(UUID id_atm) throws NotFoundException {
        List<Arching>archingList=archingRepository.findArchingByIdAtm(id_atm)
                .orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND));
        return ArchingMovement.CreateListArchingWithAtmDTO(archingList);
    }

    //Me traer todos los arqueos por el nombre del ATM
    @Override
    public List<ArchingWithAtmDTO> getArchingByNameATM(String name_ATM) throws NotFoundException {
        List<Arching>archingList=archingRepository.findArchingByNameAtm(name_ATM)
                .orElseThrow(() -> new NotFoundException(ATM_NOT_FOUND));
        return ArchingMovement.CreateListArchingWithAtmDTO(archingList);
    }

    //Me traer todos los arqueos por el id del box
    @Override
    public List<ArchingWithBoxDTO> getArchingByBox(UUID id_box) throws NotFoundException {
        List<Arching>archingList=archingRepository.findArchingByIdBox(id_box)
                .orElseThrow(() -> new NotFoundException(BOX_NOT_FOUND));
        return ArchingMovement.CreateListArchingWithBoxDTO(archingList);
    }

    //Me traer todos los arqueos por el nombre del box
    @Override
    public List<ArchingWithBoxDTO> getArchingByNameBox(String name_BOX) throws NotFoundException {
        List<Arching>archingList=archingRepository.findArchingByNameBox(name_BOX)
                .orElseThrow(() -> new NotFoundException(BOX_NOT_FOUND));
        return ArchingMovement.CreateListArchingWithBoxDTO(archingList);
    }



}
