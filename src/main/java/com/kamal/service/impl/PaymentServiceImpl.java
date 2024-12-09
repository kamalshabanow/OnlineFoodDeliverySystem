package com.kamal.service.impl;

import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;
import com.kamal.entity.Order;
import com.kamal.entity.Payment;
import com.kamal.entity.User;
import com.kamal.repository.OrderRepository;
import com.kamal.repository.PaymentRepository;
import com.kamal.repository.UserRepository;
import com.kamal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();

        return modelMapper.map(payment, PaymentResponseDTO.class);
    }

    @Transactional
    @Override
    public String addPayment(PaymentRequestDTO paymentRequestDTO) {
        User user = userRepository.findById(paymentRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(paymentRequestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));


        Payment payment = modelMapper.map(paymentRequestDTO, Payment.class);

        payment.setUser(user);
        payment.setOrder(order);
        paymentRepository.save(payment);

        return "Payment added successfully";
    }

    @Transactional
    @Override
    public String updatePayment(Long id, PaymentRequestDTO paymentRequestDTO) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        modelMapper.map(paymentRequestDTO,payment);
        paymentRepository.save(payment);

        return "Payment updated successfully";
    }

    @Transactional
    @Override
    public String deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        paymentRepository.delete(payment);

        return "Payment deleted successfully";
    }
}
