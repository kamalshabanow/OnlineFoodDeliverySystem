package com.kamal.repository;

import com.kamal.constant.OrderStatus;
import com.kamal.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus orderStatus);

    Long countByStatus(OrderStatus orderStatus);

    List<Order> findTop10ByOrderByCreatedAtDesc(Pageable pageable);

    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
