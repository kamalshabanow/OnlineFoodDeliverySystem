package com.kamal.service;

import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentResponseDTO> getAllPayments();
    PaymentResponseDTO getPaymentById(Long id);
    String addPayment(PaymentRequestDTO paymentRequestDTO);
    String updatePayment(Long id,PaymentRequestDTO paymentRequestDTO);
    String deletePayment(Long id);
}
