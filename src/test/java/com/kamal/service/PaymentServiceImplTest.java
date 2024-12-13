package com.kamal.service;

import com.kamal.constant.OrderStatus;
import com.kamal.constant.PaymentMethod;
import com.kamal.constant.PaymentStatus;
import com.kamal.dto.request.PaymentRequestDTO;
import com.kamal.dto.response.PaymentResponseDTO;
import com.kamal.entity.Order;
import com.kamal.entity.Payment;
import com.kamal.entity.User;
import com.kamal.repository.OrderRepository;
import com.kamal.repository.PaymentRepository;
import com.kamal.repository.UserRepository;
import com.kamal.service.impl.PaymentServiceImpl;
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
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;


    @Test
    void testGetAllPayments(){
        Payment payment = Payment.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        PaymentResponseDTO responseDTO = PaymentResponseDTO.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment));
        when(modelMapper.map(payment,PaymentResponseDTO.class)).thenReturn(responseDTO);

        List<PaymentResponseDTO> result = paymentService.getAllPayments();

        assertNotNull(result);

        assertEquals(responseDTO.getAmount(),result.getFirst().getAmount());
        assertEquals(responseDTO.getPaymentMethod(),result.getFirst().getPaymentMethod());
        assertEquals(responseDTO.getPaymentStatus(),result.getFirst().getPaymentStatus());

        verify(paymentRepository,times(1)).findAll();
        verify(modelMapper,times(1)).map(payment,PaymentResponseDTO.class);
    }

    @Test
    void testGetPaymentById(){

        Payment payment = Payment.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        PaymentResponseDTO responseDTO = PaymentResponseDTO.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(modelMapper.map(payment,PaymentResponseDTO.class)).thenReturn(responseDTO);

        PaymentResponseDTO result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getAmount(),result.getAmount());
        assertEquals(responseDTO.getPaymentMethod(),result.getPaymentMethod());
        assertEquals(responseDTO.getPaymentStatus(),result.getPaymentStatus());

        verify(paymentRepository,times(1)).findById(1L);
        verify(modelMapper,times(1)).map(payment,PaymentResponseDTO.class);
    }

    @Test
    void testAddPayment() {

        Payment payment = Payment.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        PaymentRequestDTO requestDTO = PaymentRequestDTO.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .orderId(1L)
                .userId(1L)
                .build();

        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .build();

        Order order = Order.builder()
                .totalPrice(50.0)
                .status(OrderStatus.PENDING)
                .build();

        when(modelMapper.map(requestDTO,Payment.class)).thenReturn(payment);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        String result = paymentService.addPayment(requestDTO);

        assertNotNull(result);
        assertEquals("Payment added successfully",result);

        verify(paymentRepository,times(1)).save(payment);
        verify(modelMapper,times(1)).map(requestDTO,Payment.class);
    }

    @Test
    void testUpdatePayment() {

        Payment payment = Payment.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        PaymentRequestDTO requestDTO = PaymentRequestDTO.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        String result = paymentService.updatePayment(1L,requestDTO);

        assertNotNull(result);

        assertEquals("Payment updated successfully",result);

        verify(paymentRepository,times(1)).findById(1L);
        verify(paymentRepository,times(1)).save(payment);
        verify(modelMapper,times(1)).map(requestDTO,payment);
    }

    @Test
    void testDeletePayment() {

        Payment payment = Payment.builder()
                .amount(100.0)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        String result = paymentService.deletePayment(1L);

        assertNotNull(result);
        assertEquals("Payment deleted successfully",result);

        verify(paymentRepository,times(1)).findById(1L);
        verify(paymentRepository,times(1)).delete(payment);

    }


}
