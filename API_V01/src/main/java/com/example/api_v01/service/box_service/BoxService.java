package com.example.api_v01.service.box_service;

import com.example.api_v01.dto.BoxDTO;
import com.example.api_v01.model.Box;

import java.util.List;
import java.util.UUID;

public interface BoxService {


    Box saveBox(BoxDTO box);

    Box toggleBoxStatus(UUID id_box);

    Box assignAtmToBox(UUID id_box, BoxDTO boxDTO);

    Box getBoxInfo(UUID boxId);

    List<Box> getBoxes();

    List<Box> getBoxesByAtm(UUID id_atm);
}
