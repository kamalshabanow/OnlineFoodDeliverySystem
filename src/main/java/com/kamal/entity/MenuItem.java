package com.kamal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name cannot be empty")
    @Column(name = "item_name",nullable = false,length = 100)
    private String name;

    @Column(name = "item_description",columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Item price cannot be null")
    @Min(value = 0,message = "Item price must be at least 0")
    @Column(name = "item_price",nullable = false)
    private Double price;

    @Column(name = "item_image_url",columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "item_availability")
    private boolean isAvailable;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
