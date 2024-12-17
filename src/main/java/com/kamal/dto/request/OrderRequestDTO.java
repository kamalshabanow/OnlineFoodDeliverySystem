package com.kamal.dto.request;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long userId;
    private Double totalPrice;
    private List<Long> menuItemIds;
    private Long restaurantId;

}
