package com.kamal.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean isAvailable;
    private Long restaurantId;
    private String restaurantName;


}
