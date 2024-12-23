package com.kamal.service;

import com.kamal.constant.OrderStatus;
import com.kamal.dto.request.OrderRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {


    //CRUD
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    String createOrder(OrderRequestDTO orderRequestDTO);
    String updateOrder(Long id,OrderRequestDTO orderRequestDTO);
    String deleteOrder(Long id);

    //Additional Methods

    List<OrderResponseDTO> getOrdersByStatus(OrderStatus orderStatus);
    List<OrderResponseDTO> getRecentOrders(int limit);
    Double calculateTotalRevenue();
    List<OrderResponseDTO> getUserOrderHistory(Long userId);
    List<OrderResponseDTO> getRestaurantOrders(Long restaurantId);
    String cancelOrder(Long orderId);
    Long countOrdersByStatus(OrderStatus orderStatus);
    List<OrderResponseDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
