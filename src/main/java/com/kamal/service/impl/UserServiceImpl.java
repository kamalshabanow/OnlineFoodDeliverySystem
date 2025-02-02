package com.kamal.service.impl;

import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.dto.response.UserResponseDTO;
import com.kamal.model.entity.User;
import com.kamal.repository.UserRepository;
import com.kamal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    // CRUD
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().
                map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        return modelMapper.map(user, UserResponseDTO.class);
    }


    @Transactional
    @Override
    public String createUser(UserRequestDTO userRequestDTO) {
        if(existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = modelMapper.map(userRequestDTO, User.class);
        user.setActive(true);
        userRepository.save(user);

        return "User created successfully";
    }

    @Transactional
    @Override
    public String updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow();
        modelMapper.map(userRequestDTO,user);
        userRepository.save(user);

        return "User updated successfully";
    }

    @Transactional
    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);

        return "User deleted successfully";
    }

    // Beside methods

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return modelMapper.map(user,UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);

        return users.stream()
                .map(user -> modelMapper.map(user,UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setActive(false);
        userRepository.save(user);

        return "User deactivated successfully";
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return user.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> getActiveUsers() {
        List<User> activeUsers = userRepository.findByActiveIsTrue();

        return activeUsers.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }


    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
