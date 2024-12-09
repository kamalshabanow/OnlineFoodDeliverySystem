package com.kamal.service;

import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    String createUser(UserRequestDTO userRequestDTO);
    String updateUser(Long id, UserRequestDTO userRequestDTO);
    String deleteUser(Long id);
}
