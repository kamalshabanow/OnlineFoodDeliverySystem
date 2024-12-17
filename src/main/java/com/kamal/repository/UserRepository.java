package com.kamal.repository;

import com.kamal.dto.response.UserResponseDTO;
import com.kamal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByActiveTrue();
    boolean existsByEmail(String email);
}
