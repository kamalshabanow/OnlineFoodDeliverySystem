package com.kamal.repository;

import com.kamal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    @Query(value = "SELECT u FROM User u WHERE u.isActive = true")
    List<User> findByActiveIsTrue();
    boolean existsByEmail(String email);
}
