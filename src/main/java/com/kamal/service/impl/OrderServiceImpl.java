package com.kamal.service.impl;

import com.kamal.dto.request.OrderRequestDTO;
import com.kamal.dto.response.OrderResponseDTO;
import com.kamal.entity.MenuItem;
import com.kamal.entity.Order;
import com.kamal.entity.Restaurant;
import com.kamal.entity.User;
import com.kamal.repository.MenuItemRepository;
import com.kamal.repository.OrderRepository;
import com.kamal.repository.RestaurantRepository;
import com.kamal.repository.UserRepository;
import com.kamal.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        modelMapper.map(orderRequestDTO,order);
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
}
