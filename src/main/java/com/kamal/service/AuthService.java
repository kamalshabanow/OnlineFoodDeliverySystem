package com.kamal.service;

import com.kamal.constant.UserRole;
import com.kamal.dto.request.AuthRequest;
import com.kamal.dto.request.UserRequestDTO;
import com.kamal.dto.response.AuthResponse;
import com.kamal.entity.User;
import com.kamal.exception.AuthenticationException;
import com.kamal.exception.InvalidPasswordException;
import com.kamal.exception.InvalidTokenException;
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
    private JwtTokenProvider tokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserService userService, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenProvider.generateToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return AuthResponse.builder()
                    .token(accessToken)
                    .refreshToken(refreshToken)
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
            if(userService.getUserByEmail(request.getEmail()) != null) {
                throw new UserAlreadyExistsException("User already existst with email: " + request.getEmail());
            }

            validatePassword(request.getPassword());

            request.setPassword(passwordEncoder.encode(request.getPassword()));

            if(request.getRole() == null) {
                request.setRole(UserRole.CUSTOMER);
            }

            return userService.createUser(request);

        }catch (Exception e) {
            log.error("Error in register: ",e);
            throw e;
        }
    }

    private void validatePassword(String password) {
        if(password != null || password.length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        //more password validation
    }

    public AuthResponse refreshToken(String refreshToken) {
        try {
            if(!tokenProvider.validateToken(refreshToken)) {
                throw new InvalidTokenException("Invalid refresh token");
            }

            String email = tokenProvider.getUsernameFromToken(refreshToken);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

            String newAccessToken = tokenProvider.generateToken(authenticationToken);
            String newRefreshToken = tokenProvider.generateRefreshToken(authenticationToken);

            return AuthResponse.builder()
                    .token(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .email(userDetails.getUsername())
                    .authorities(userDetails.getAuthorities())
                    .build();
        } catch (Exception e) {
            log.error("Could not refresh token",e);
            throw new InvalidTokenException("Could not refresh token");
        }

    }

    public void logout(String token) {

        SecurityContextHolder.clearContext();
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if(!passwordEncoder.matches(oldPassword,user.getPassword())) {
                throw new InvalidPasswordException("Invalid old password");
            }

            validatePassword(newPassword);

            userService.changeUserPassword(userId,passwordEncoder.encode(newPassword));
        } catch (Exception e) {
            log.error("Error in changePassword: ",e);
            throw e;
        }
    }

    public void resetPassword(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            String temporaryPassword = generateTemporaryPassword();
            userService.changeUserPassword(user.getId(),passwordEncoder.encode(temporaryPassword));
        } catch (Exception e) {
            log.error("Error in resetPassword: ", e);
            throw e;
        }
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0,8);
    }
}
