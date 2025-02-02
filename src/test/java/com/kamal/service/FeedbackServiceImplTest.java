package com.kamal.service;

import com.kamal.model.constant.OrderStatus;
import com.kamal.dto.request.FeedbackRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.entity.*;
import com.kamal.model.entity.Feedback;
import com.kamal.model.entity.Order;
import com.kamal.model.entity.Restaurant;
import com.kamal.model.entity.User;
import com.kamal.repository.*;
import com.kamal.service.impl.FeedbackServiceImpl;
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
public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;


    @Test
    void testGetAllFeedbacks() {
        Feedback feedback = Feedback.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        FeedbackResponseDTO responseDTO = FeedbackResponseDTO.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback));
        when(modelMapper.map(feedback,FeedbackResponseDTO.class)).thenReturn(responseDTO);

        List<FeedbackResponseDTO> result = feedbackService.getAllFeedbacks();

        assertNotNull(result);
        assertEquals(responseDTO.getRating(),result.getFirst().getRating());
        assertEquals(responseDTO.getComment(),result.getFirst().getComment());

        verify(feedbackRepository,times(1)).findAll();
        verify(modelMapper,times(1)).map(feedback,FeedbackResponseDTO.class);
    }

    @Test
    void testGetFeedbackById() {

        Feedback feedback = Feedback.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        FeedbackResponseDTO responseDTO = FeedbackResponseDTO.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));
        when(modelMapper.map(feedback,FeedbackResponseDTO.class)).thenReturn(responseDTO);

        FeedbackResponseDTO result = feedbackService.getFeedbackById(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getRating(),result.getRating());
        assertEquals(responseDTO.getComment(),result.getComment());

        verify(feedbackRepository,times(1)).findById(1L);
        verify(modelMapper,times(1)).map(feedback,FeedbackResponseDTO.class);
    }

    @Test
    void testCreateFeedback() {

        Feedback feedback = Feedback.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        FeedbackRequestDTO requestDTO = FeedbackRequestDTO.builder()
                .rating(5.0)
                .comment("Good")
                .userId(1L)
                .restaurantId(1L)
                .orderId(1L)
                .build();

        User user = User.builder()
                .name("Kamal")
                .surname("Shabanov")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Milano")
                .address("Baku")
                .build();

        Order order = Order.builder()
                .totalPrice(50.0)
                .status(OrderStatus.PENDING)
                .build();

        when(modelMapper.map(requestDTO,Feedback.class)).thenReturn(feedback);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        String result = feedbackService.createFeedback(requestDTO);

        assertNotNull(result);
        assertEquals("Feedback created successfully",result);

        verify(feedbackRepository,times(1)).save(feedback);
        verify(modelMapper,times(1)).map(requestDTO,Feedback.class);

    }

    @Test
    void testUpdateFeedback() {

        Feedback feedback = Feedback.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        FeedbackRequestDTO requestDTO = FeedbackRequestDTO.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        String result = feedbackService.updateFeedback(1L, requestDTO);

        assertNotNull(result);
        assertEquals("Feedback updated successfully",result);

        verify(feedbackRepository,times(1)).findById(1L);
        verify(feedbackRepository,times(1)).save(feedback);
        verify(modelMapper,times(1)).map(requestDTO,feedback);
    }

    @Test
    void testDeleteFeedback(){

        Feedback feedback = Feedback.builder()
                .rating(5.0)
                .comment("Good")
                .build();

        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        String result = feedbackService.deleteFeedback(1L);

        assertNotNull(result);
        assertEquals("Feedback deleted successfully",result);

        verify(feedbackRepository,times(1)).findById(1L);
        verify(feedbackRepository,times(1)).delete(feedback);
    }
}
