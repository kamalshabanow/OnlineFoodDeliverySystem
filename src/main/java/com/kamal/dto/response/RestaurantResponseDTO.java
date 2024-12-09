package com.kamal.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Double rating;
    private boolean isOpen;
    private String cuisineType;
    private Double deliveryFee;
}
