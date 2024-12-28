package com.kamal.service;

import com.kamal.constant.UserRole;
import com.kamal.dto.request.RegisterRequestDTO;
import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.UserResponseDTO;
import com.kamal.entity.User;
import com.kamal.repository.UserRepository;
import com.kamal.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testGetAllUsers(){
        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(responseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals("Kamal",result.get(0).getName());
        assertEquals("Shabanov",result.get(0).getSurname());
        assertEquals("kamalshabanov@gmail.com",result.get(0).getEmail());
        assertEquals("+994557863562",result.get(0).getPhoneNumber());


        verify(userRepository,times(1)).findAll();
        verify(modelMapper,times(1)).map(user, UserResponseDTO.class);

    }

    @Test
    void testGetUserById() {
        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(responseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Kamal",result.getName());
        verify(userRepository,times(1)).findById(1L);
        verify(modelMapper,times(1)).map(user, UserResponseDTO.class);
    }

    @Test
    void testCreateUser() {

        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        when(modelMapper.map(requestDTO,User.class)).thenReturn(user);

        String result = userService.createUser(requestDTO);

        assertNotNull(result);
        assertEquals("User created successfully",result);
        verify(userRepository,times(1)).save(user);

    }

    @Test
    void testUpdateUser() {

        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.updateUser(1L,requestDTO);

        assertEquals("User updated successfully",result);

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser(){

        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .email("kamalshabanov@gmail.com")
                .phoneNumber("+994557863562")
                .build();


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1L);

        assertEquals("User deleted successfully",result);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);

    }
}
