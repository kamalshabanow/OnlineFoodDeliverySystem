package com.kamal.dto.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
