package com.kamal.controller;

import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;
import com.kamal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public List<PaymentResponseDTO> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getPaymentById(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String addPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.addPayment(paymentRequestDTO);
    }

    @PutMapping("/update/{id}")
    public String updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.updatePayment(id,paymentRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id){
        return paymentService.deletePayment(id);
    }
}
