package com.kamal.controller;

import com.kamal.constant.PaymentStatus;
import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;
import com.kamal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    //CRUD Endpoints

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> addPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return new ResponseEntity<>(paymentService.addPayment(paymentRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.ok(paymentService.updatePayment(id, paymentRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }


    //Additional Endpoints

    @GetMapping("/user-payment-history/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getUserPaymentHistory(
            @PathVariable Long userId) {

        return ResponseEntity.ok(paymentService.getUserPaymentHistory(userId));
    }

    @GetMapping("/payment-by-order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));
    }

    @GetMapping("/payments-date-range")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        return ResponseEntity.ok(paymentService.getPaymentsByDateRange(startDate, endDate));
    }

    @GetMapping("/completed-payments")
    public ResponseEntity<List<PaymentResponseDTO>> getCompletedPayments() {
        return ResponseEntity.ok(paymentService.getCompletedPayments());
    }

    @PutMapping("/refund-payment/{paymentId}")
    public ResponseEntity<String> refundPayment(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    @PutMapping("/process-payment/{paymentId}")
    public ResponseEntity<String> processPayment(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(paymentService.processPayment(paymentId));
    }

    @GetMapping("/payments-by-status")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByStatus(
            @RequestParam PaymentStatus status) {

        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @GetMapping("/recent-payments")
    public ResponseEntity<List<PaymentResponseDTO>> getRecentPayments(
            @RequestParam int limit) {

        return ResponseEntity.ok(paymentService.getRecentPayments(limit));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePayments(
            @RequestBody PaymentRequestDTO paymentRequestDTO) {

        return ResponseEntity.ok(paymentService.isPaymentValid(paymentRequestDTO));
    }


}
