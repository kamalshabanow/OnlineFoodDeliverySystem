//package com.kamal.util;
//
//import com.kamal.constant.UserRole;
//import com.kamal.entity.User;
//import com.kamal.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class AdminInitializer {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void initAdmin(){
//        if(userRepository.findByEmail("admin@gmail.com").isEmpty()) {
//            User user = new User();
//            user.setEmail("admin@gmail.com");
//            user.setPassword(passwordEncoder.encode("admin"));
//            user.setRole(UserRole.ADMIN);
//
//            userRepository.save(user);
//            System.out.println("Admin created");
//        }
//    }
//
//
//}
