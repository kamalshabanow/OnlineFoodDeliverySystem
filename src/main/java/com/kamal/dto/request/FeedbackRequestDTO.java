package com.kamal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {

    private Double rating;
    private String comment;
    private Long userId;
    private Long restaurantId;
    private Long orderId;
}
