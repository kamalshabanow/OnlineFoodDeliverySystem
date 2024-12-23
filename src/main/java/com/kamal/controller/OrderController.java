package com.kamal.controller;

import com.kamal.dto.request.OrderRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return new ResponseEntity<>(orderService.createOrder(orderRequestDTO),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.ok(orderService.updateOrder(id,orderRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        return orderService.deleteOrder(id);
    }
}
