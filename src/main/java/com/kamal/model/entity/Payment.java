package com.kamal.model.entity;


import com.kamal.model.constant.PaymentMethod;
import com.kamal.model.constant.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0,message = "Amount must be at least 0")
    @Column(name = "payment_amount",nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method",nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status",nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at",nullable = false)
    private LocalDateTime paidAt;

    @PrePersist
    public void setPaidAt(){
        this.paidAt = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
