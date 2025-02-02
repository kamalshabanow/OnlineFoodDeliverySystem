package com.kamal.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "feedback")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1,message = "Rating must be between 1 and 5")
    @Max(value = 5,message = "Rating must be between 1 and 5")
    @Column(name = "feedback_rating")
    private Double rating;

    @Column(name = "feedback_comment",columnDefinition = "TEXT")
    private String comment;

    @Column(name = "feedback_created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "feedback_updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void feedbackCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void feedbackUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
