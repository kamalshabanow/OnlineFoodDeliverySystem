package com.kamal.dto.request;

import com.kamal.constant.PaymentMethod;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {

    private Double amount;
    private PaymentMethod paymentMethod;
    private Long userId;
    private Long orderId;
}
