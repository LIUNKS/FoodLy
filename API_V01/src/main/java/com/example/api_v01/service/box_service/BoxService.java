package com.example.api_v01.service.box_service;

import com.example.api_v01.model.Box;

import java.util.UUID;

public interface BoxService {
    Box createBox(String name, UUID adminId);
    Box toggleBoxStatus(UUID boxId, boolean newStatus);
    Box assignAtmToBox(UUID boxId, UUID atmId);
    boolean getBoxStatus(UUID boxId);
    Box getBoxInfo(UUID boxId);
}
