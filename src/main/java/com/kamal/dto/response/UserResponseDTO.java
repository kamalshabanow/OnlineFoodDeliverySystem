package com.kamal.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive;

}
