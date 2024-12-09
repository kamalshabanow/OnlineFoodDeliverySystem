package com.kamal.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long userId;
    private Double totalPrice;
    private List<Long> menuItemIds;
    private Long restaurantId;

}





