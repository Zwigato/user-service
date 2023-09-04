package com.app.userinfo.service;

import com.app.userinfo.dto.UserDTO;
import com.app.userinfo.entity.User;
import com.app.userinfo.mapper.UserMapper;
import com.app.userinfo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public UserDTO addUser(UserDTO userDTO) {
        User savedUser = userRepo.save(UserMapper.INSTANCE.mapUserDTOToUser(userDTO));
        return UserMapper.INSTANCE.mapUserToUserDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream().map(user -> UserMapper.INSTANCE.mapUserToUserDTO(user)).collect(Collectors.toList());
    }

    public ResponseEntity fetchUserById(Integer userId) {
        Optional<User> user = userRepo.findById(userId);
            if(user.isPresent())
                return new ResponseEntity(UserMapper.INSTANCE.mapUserToUserDTO(user.get()), HttpStatus.FOUND);
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}
