package com.kamal.service;

import com.kamal.dto.request.RegisterRequestDTO;
import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    //CRUD
    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    String createUser(UserRequestDTO userRequestDTO);

    String updateUser(Long id, UserRequestDTO userRequestDTO);

    String deleteUser(Long id);

    //Beside methods
    UserResponseDTO getUserByEmail(String email);

    List<UserResponseDTO> getUsersByName(String name);

    String deactivateUser(Long id);

    List<OrderResponseDTO> getUserOrders(Long userId);

    List<UserResponseDTO> getActiveUsers();
}
