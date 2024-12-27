package com.kamal.security.service;

import com.kamal.entity.User;
import com.kamal.repository.UserRepository;
import com.kamal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                    .accountExpired(!user.isActive())
                    .accountLocked(!user.isActive())
                    .credentialsExpired(!user.isActive())
                    .disabled(!user.isActive())
                    .build();
        } catch (Exception e) {
            log.error("Error in loadByUsername: " + e);
            throw e;
        }

    }
}
