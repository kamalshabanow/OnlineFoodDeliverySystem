package com.kamal.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private String refreshToken;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
}
