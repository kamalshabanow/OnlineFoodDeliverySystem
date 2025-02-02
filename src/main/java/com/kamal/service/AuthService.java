package com.kamal.service;

import com.kamal.model.constant.UserRole;
import com.kamal.dto.request.AuthRequest;
import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.AuthResponse;
import com.kamal.model.entity.User;
import com.kamal.exception.AuthenticationException;
import com.kamal.exception.InvalidPasswordException;
import com.kamal.exception.UserAlreadyExistsException;
import com.kamal.repository.UserRepository;
import com.kamal.security.JwtTokenProvider;
import com.kamal.security.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
public class AuthService {


    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtTokenProvider.generateToken(authentication);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());

            return AuthResponse.builder()
                    .token(accessToken)
                    .email(userDetails.getUsername())
                    .authorities(userDetails.getAuthorities())
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Invalid email or password", e);
            throw new AuthenticationException("Invalid email or password");
        }
    }

    public String register(UserRequestDTO request) {

        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
            }
            request.setEmail(request.getEmail());

            validatePassword(request.getPassword());
            request.setPassword(passwordEncoder.encode(request.getPassword()));

            if (request.getRole() == null) {
                request.setRole(UserRole.CUSTOMER);
            }

            return userService.createUser(request);

        } catch (Exception e) {
            log.error("Error in register: ", e);
            throw e;
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        //more password validation
    }


    public void logout(String token) {

        SecurityContextHolder.clearContext();
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new InvalidPasswordException("Invalid old password");
            }

            validatePassword(newPassword);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error in changePassword: ", e);
            throw e;
        }
    }


    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
