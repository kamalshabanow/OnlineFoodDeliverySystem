package com.kamal.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRequestDTO {

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean isAvailable;
    private Long restaurantId;

}
