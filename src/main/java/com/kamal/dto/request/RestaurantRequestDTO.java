package com.kamal.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequestDTO {

    private String name;
    private String address;
    private String phoneNumber;
    private Double rating;
    private String cuisineType;
    private Double deliveryFee;

}
