package com.kamal.service;

import com.kamal.constant.PaymentStatus;
import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {

    //CRUD
    List<PaymentResponseDTO> getAllPayments();

    PaymentResponseDTO getPaymentById(Long id);

    String addPayment(PaymentRequestDTO paymentRequestDTO);

    String updatePayment(Long id, PaymentRequestDTO paymentRequestDTO);

    String deletePayment(Long id);


    //Additional Methods
    PaymentResponseDTO getPaymentByOrder(Long orderId);

    List<PaymentResponseDTO> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<PaymentResponseDTO> getCompletedPayments();

    //claude
    String refundPayment(Long paymentId);

    String processPayment(Long paymentId);

    List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status);

    List<PaymentResponseDTO> getUserPaymentHistory(Long userId);

    List<PaymentResponseDTO> getRecentPayments(int limit);

    boolean isPaymentValid(PaymentRequestDTO paymentRequestDTO);
}
