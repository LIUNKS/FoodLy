package com.example.api_v01.service.user_service;

import com.example.api_v01.dto.entityLike.UserDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User SaveUser(UserDTO userDTO);
    User GetUserById(UUID id) throws NotFoundException;
    List<User> getUsers();
    void DeleteUser(UUID id) throws NotFoundException;
    User UpdateUser(UUID id,UserDTO userDTO) throws NotFoundException, BadRequestException;
}
