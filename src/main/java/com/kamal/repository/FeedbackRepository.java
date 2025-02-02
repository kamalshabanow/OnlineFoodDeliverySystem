package com.kamal.repository;

import com.kamal.model.entity.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId, Pageable pageable);

    boolean existsByUserIdAndOrderId(Long userId, Long orderId);

    List<Feedback> findByRatingLessThanAndCreatedAtAfter(Double rating, LocalDateTime date);

    List<Feedback> findByRestaurantIdAndRatingLessThanAndCreatedAtBefore(Long restaurantId, Double rating, LocalDateTime date);
}
