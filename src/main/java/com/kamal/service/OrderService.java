package com.kamal.service;

import com.kamal.dto.request.OrderRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    String createOrder(OrderRequestDTO orderRequestDTO);
    String updateOrder(Long id,OrderRequestDTO orderRequestDTO);
    String deleteOrder(Long id);
}
