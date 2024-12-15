package com.kamal.controller;

import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.dto.response.UserResponseDTO;
import com.kamal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //CRUD
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    @PutMapping("/update-user/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    //Beside methods

    @GetMapping("/by-email/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/search-by-name/{name}")
    public List<UserResponseDTO> searchUsersByName(@PathVariable String name) {
        return userService.searchUsersByName(name);
    }


    @PutMapping("/deactivate-user/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }


    @PutMapping("/change-password/{id}")
    public ResponseEntity<String> changeUserPassword(@PathVariable Long id, @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.changeUserPassword(id, newPassword));
    }

    @GetMapping("/user-orders/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserOrders(userId));
    }

}
