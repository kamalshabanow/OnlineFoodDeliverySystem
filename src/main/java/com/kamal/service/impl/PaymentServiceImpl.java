package com.kamal.service.impl;

import com.kamal.model.constant.PaymentStatus;
import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;
import com.kamal.model.entity.Order;
import com.kamal.model.entity.Payment;
import com.kamal.model.entity.User;
import com.kamal.repository.OrderRepository;
import com.kamal.repository.PaymentRepository;
import com.kamal.repository.UserRepository;
import com.kamal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    //CRUD

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
        modelMapper.map(paymentRequestDTO, payment);
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

    //Additional Methods

    @Override
    public List<PaymentResponseDTO> getUserPaymentHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getPayments().stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO getPaymentByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return modelMapper.map(order.getPayment(), PaymentResponseDTO.class);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> payments = paymentRepository.findByPaidAtBetween(startDate, endDate);

        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> getCompletedPayments() {
        List<Payment> payments = paymentRepository.findByPaymentStatus(PaymentStatus.COMPLETED);

        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .toList();
    }

    @Override
    public String refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.CANCELED);
        paymentRepository.save(payment);

        return "Payment refunded successfully";
    }

    @Override
    public String processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if(payment.getPaymentStatus() == PaymentStatus.PENDING) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(payment);

            return "Payment processed successfully";
        }

        throw new RuntimeException("Payment cannot be processed");
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByPaymentStatus(status);

        return payments.stream()
                .map(payment -> modelMapper.map(payment,PaymentResponseDTO.class))
                .toList();
    }


    @Override
    public List<PaymentResponseDTO> getRecentPayments(int limit) {
        List<Payment> recentPayments = paymentRepository
                .findByOrderByPaidAtDesc(PageRequest.of(0, limit));

        return recentPayments.stream()
                .map(payment -> modelMapper.map(payment,PaymentResponseDTO.class))
                .toList();
    }

    @Override
    public boolean isPaymentValid(PaymentRequestDTO paymentRequestDTO) {
        if(paymentRequestDTO.getAmount() <= 0) return false;

        Order order = orderRepository.findById(paymentRequestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return Math.abs(paymentRequestDTO.getAmount() - order.getTotalPrice()) < 0.01;
    }
}
