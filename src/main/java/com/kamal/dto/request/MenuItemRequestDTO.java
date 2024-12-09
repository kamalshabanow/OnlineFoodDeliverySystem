package com.kamal.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequestDTO {

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean isAvailable;
    private Long restaurantId;

}
