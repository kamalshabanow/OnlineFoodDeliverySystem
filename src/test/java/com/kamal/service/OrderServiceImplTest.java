package com.kamal.service;

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
import com.kamal.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void testGetAllOrders() {

        Order order = Order.builder()
                .totalPrice(50.0)
                .status(OrderStatus.PENDING)
                .build();

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .totalPrice(50.0)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(modelMapper.map(order, OrderResponseDTO.class)).thenReturn(responseDTO);


        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertNotNull(result);

        assertEquals(responseDTO.getTotalPrice(), result.get(0).getTotalPrice());
        assertEquals(responseDTO.getStatus(), result.get(0).getStatus());


        verify(orderRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(order, OrderResponseDTO.class);

    }

    @Test
    void testGetOrderById() {

        Order order = Order.builder()
                .totalPrice(50.0)
                .status(OrderStatus.PENDING)
                .build();

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .totalPrice(50.0)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(modelMapper.map(order, OrderResponseDTO.class)).thenReturn(responseDTO);

        OrderResponseDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getTotalPrice(), result.getTotalPrice());
        assertEquals(responseDTO.getStatus(), result.getStatus());

        verify(orderRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(order, OrderResponseDTO.class);
    }

    @Test
    void testCreateOrder() {

        Order order = Order.builder()
                .totalPrice(50.0)
                .build();

        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .userId(1L)
                .restaurantId(1L)
                .menuItemIds(Arrays.asList(1L))
                .totalPrice(50.0)
                .build();

        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Milano")
                .address("Baku")
                .build();

        MenuItem menuItem = MenuItem.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .build();

        when(modelMapper.map(requestDTO, Order.class)).thenReturn(order);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when((restaurantRepository.findById(1L))).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        String result = orderService.createOrder(requestDTO);

        assertNotNull(result);
        assertEquals("Order created successfully", result);

        verify(orderRepository, times(1)).save(order);
        verify(modelMapper, times(1)).map(requestDTO, Order.class);
    }


    @Test
    void testUpdateOrder() {

        Order order = Order.builder()
                .totalPrice(50.0)
                .build();

        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .totalPrice(75.0)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));


        String result = orderService.updateOrder(1L, requestDTO);

        assertNotNull(result);
        assertEquals("Order updated successfully", result);

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
        verify(modelMapper, times(1)).map(requestDTO, order);
    }

    @Test
    void testDeleteOrder() {

        Order order = Order.builder()
                .totalPrice(50.0)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        String result = orderService.deleteOrder(1L);

        assertNotNull(result);
        assertEquals("Order deleted successfully", result);

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).delete(order);


    }


}
