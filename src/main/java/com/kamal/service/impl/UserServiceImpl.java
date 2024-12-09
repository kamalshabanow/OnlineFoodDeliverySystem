package com.kamal.service.impl;

import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.UserResponseDTO;
import com.kamal.entity.User;
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
}
