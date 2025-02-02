package com.kamal.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Restaurant name cannot be empty")
    @Column(name = "restaurant_name",nullable = false)
    private String name;

    @NotBlank(message = "Restaurant address cannot be empty")
    @Column(name = "restaurant_address",nullable = false)
    private String address;

    @NotBlank(message = "Restaurant phone number cannot be empty")
    @Pattern(regexp = "\\d{10,20}",message = "Phone number must contain 10-20 digits")
    @Column(name = "restaurant_phone_number",nullable = false,length = 20)
    private String phoneNumber;

    @Min(value = 0,message = "Rating must be at least 0")
    @Max(value = 5,message = "Rating must not exceed 5")
    @Column(name = "restaurant_rating",nullable = false)
    private Double rating;

    @Column(name = "restaurant_activity")
    private boolean isOpen;

    @NotBlank(message = "Cuisine type cannot be empty")
    @Size(max = 50,message = "Cuisine type must not exceed 50 characters")
    @Column(name = "restaurant_cuisine_type",nullable = false,length = 50)
    private String cuisineType;

    @Min(value = 0,message = "Delivery fee must be at least 0")
    @Column(name = "restaurant_delivery_fee",nullable = false)
    private Double deliveryFee;


    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Feedback> feedbacks;

}
