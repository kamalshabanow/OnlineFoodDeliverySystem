package com.kamal.dto.response;


import com.kamal.constant.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long restaurantId;
    private List<MenuItemResponseDTO> menuItems;

}
