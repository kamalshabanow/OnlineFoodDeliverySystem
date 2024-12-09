package com.kamal.dto.response;

import com.kamal.constant.PaymentMethod;
import com.kamal.constant.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long id;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;
    private Long userId;
    private Long orderId;
}
