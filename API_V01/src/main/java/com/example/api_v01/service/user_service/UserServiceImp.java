package com.example.api_v01.service.user_service;

import com.example.api_v01.dto.UserDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.User;
import com.example.api_v01.repository.UserRepository;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.UserMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService, ExceptionMessage {

    private final UserRepository userRepository;

    @Override
    public User SaveUser(UserDTO userDTO) {
        User user = UserMovement.createUser(userDTO);
        return userRepository.save(user);
    }

    @Override
    public User GetUserById(UUID id) throws NotFoundException {
        Optional<User>UserOptional = userRepository.findById(id);
        if (!UserOptional.isPresent()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return UserOptional.get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void DeleteUser(UUID id) throws NotFoundException {
        Optional<User>UserOptional = userRepository.findById(id);
        if (!UserOptional.isPresent()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User UpdateUser(UUID id,UserDTO userDTO) throws NotFoundException,BadRequestException {
        Optional<User>UserOptional = userRepository.findById(id);

        if (!UserOptional.isPresent()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        User user = UserOptional.get();

        if(userDTO.getUsername() != null){
            user.setUsername(userDTO.getUsername());
        }else{
            throw new BadRequestException(USER_USERNAME_INVALID);
        }

        if(userDTO.getPassword() != null){
            user.setPassword(userDTO.getPassword());
        }else{
            throw new BadRequestException(USER_PASSWORD_INVALID);
        }

        if(userDTO.getRole() != null){
            user.setRole(userDTO.getRole());
        }else{
            throw new BadRequestException(USER_ROLE_INVALID);
        }

        return userRepository.save(user);
    }

}
