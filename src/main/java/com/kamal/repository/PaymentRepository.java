package com.kamal.repository;

import com.kamal.model.constant.PaymentStatus;
import com.kamal.model.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByPaidAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Payment> findByPaymentStatus(PaymentStatus status);
    List<Payment> findByOrderByPaidAtDesc(Pageable pageable);
}
