package com.kamal.model.entity;

import com.kamal.model.constant.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Total price cannot be null")
    @Min(value = 0,message = "Total price must be at least 0")
    @Column(name = "order_total_price",nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @Column(name = "order_created_at")
    private LocalDateTime createdAt;

    @Column(name = "order_updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void orderCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void orderUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<MenuItem> menuItems;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private Payment payment;
}
