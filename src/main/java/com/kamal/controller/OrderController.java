package com.kamal.controller;

import com.kamal.dto.request.OrderRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.createOrder(orderRequestDTO);
    }

    @PutMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.updateOrder(id,orderRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        return orderService.deleteOrder(id);
    }
}
