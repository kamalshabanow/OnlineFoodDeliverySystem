package com.kamal.dto.request;

import com.kamal.constant.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    private Double amount;
    private PaymentMethod paymentMethod;
    private Long userId;
    private Long orderId;
}
