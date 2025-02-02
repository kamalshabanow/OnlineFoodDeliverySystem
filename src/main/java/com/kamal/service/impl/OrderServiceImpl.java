package com.kamal.service.impl;

import com.kamal.model.constant.OrderStatus;
import com.kamal.dto.request.OrderRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.model.entity.MenuItem;
import com.kamal.model.entity.Order;
import com.kamal.model.entity.Restaurant;
import com.kamal.model.entity.User;
import com.kamal.repository.MenuItemRepository;
import com.kamal.repository.OrderRepository;
import com.kamal.repository.RestaurantRepository;
import com.kamal.repository.UserRepository;
import com.kamal.service.OrderService;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Transactional
    @Override
    public String createOrder(OrderRequestDTO orderRequestDTO) {

        User user = userRepository.findById(orderRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(orderRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        List<MenuItem> menuItems = orderRequestDTO.getMenuItemIds().stream()
                .map(id -> menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found")))
                .toList();


        Order order = modelMapper.map(orderRequestDTO, Order.class);

        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setMenuItems(menuItems);
        orderRepository.save(order);

        return "Order created successfully";
    }

    @Transactional
    @Override
    public String updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(id).orElseThrow();
        modelMapper.map(orderRequestDTO, order);
        orderRepository.save(order);

        return "Order updated successfully";
    }

    @Transactional
    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);

        return "Order deleted successfully";
    }

    //Additional Methods

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByStatus(orderStatus);

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getRecentOrders(int limit) {
        List<Order> recentOrders = orderRepository
                .findTop10ByOrderByCreatedAtDesc(PageRequest.of(0, limit));

        return recentOrders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Double calculateTotalRevenue() {
        return orderRepository.findByStatus(OrderStatus.DELIVERED)
                .stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    @Override
    public String updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);

        return "Order status has updated successfully";
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getUserOrderHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getRestaurantOrders(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel order after delivered");
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return "Order canceled successfully";
    }

    @Transactional(readOnly = true)
    @Override
    public Long countOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.countByStatus(orderStatus);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByCreatedAtBetween(startDate, endDate);

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .toList();
    }
}
