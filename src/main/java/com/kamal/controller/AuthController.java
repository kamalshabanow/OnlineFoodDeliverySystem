package com.kamal.controller;

import com.kamal.dto.request.AuthRequest;
import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.AuthResponse;
import com.kamal.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.register(requestDTO));
    }


//    @PostMapping("/logout")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
//        authService.logout(token.substring(7));
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/change-password/{userId}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Void> changePassword(
//            @RequestBody ChangePasswordRequest request) {
//
//        authService.changePassword(request.getUserId(),request.getOldPassword(),request.getNewPassword());
//
//        return ResponseEntity.ok().build();
//    }


}
