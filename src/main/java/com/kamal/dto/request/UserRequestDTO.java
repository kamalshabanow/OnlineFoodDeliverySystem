package com.kamal.dto.request;

import com.kamal.constant.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private UserRole role;
}
