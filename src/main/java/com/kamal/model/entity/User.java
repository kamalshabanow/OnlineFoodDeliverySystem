package com.kamal.model.entity;

import com.kamal.model.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false,length = 50)
    private String name;

    @Column(name = "surname",nullable = false,length = 50)
    private String surname;

    @Column(name = "user_email",nullable = false,unique = true,length = 100)
    private String email;

    @Column(name = "user_password",nullable = false)
    private String password;

    @Column(name = "user_phone_number",nullable = false,length = 40)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",nullable = false)
    private UserRole role;

    @Column(name = "user_address",nullable = false)
    private String address;

    @Column(name = "user_activity")
    private boolean isActive;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Payment> payments;
}
