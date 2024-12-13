package com.kamal.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackRequestDTO {

    private Double rating;
    private String comment;
    private Long userId;
    private Long restaurantId;
    private Long orderId;
}
