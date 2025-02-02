package com.kamal.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamal.model.constant.UserRole;
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
    @JsonIgnore
    private UserRole role;
}
