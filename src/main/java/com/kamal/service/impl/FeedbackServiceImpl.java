package com.kamal.service.impl;

import com.kamal.dto.request.FeedbackRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.entity.Feedback;
import com.kamal.entity.Order;
import com.kamal.entity.Restaurant;
import com.kamal.entity.User;
import com.kamal.repository.FeedbackRepository;
import com.kamal.repository.OrderRepository;
import com.kamal.repository.RestaurantRepository;
import com.kamal.repository.UserRepository;
import com.kamal.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<FeedbackResponseDTO> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
                .map(feedback -> modelMapper.map(feedback,FeedbackResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackResponseDTO getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();

        return modelMapper.map(feedback,FeedbackResponseDTO.class);
    }

    @Transactional
    @Override
    public String createFeedback(FeedbackRequestDTO feedbackRequestDTO) {

        User user = userRepository.findById(feedbackRequestDTO.getUserId())
                .orElseThrow(() ->  new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(feedbackRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = orderRepository.findById(feedbackRequestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Feedback feedback = modelMapper.map(feedbackRequestDTO, Feedback.class);

        feedback.setUser(user);
        feedback.setRestaurant(restaurant);
        feedback.setOrder(order);
        feedbackRepository.save(feedback);

        return "Feedback created successfully";
    }

    @Transactional
    @Override
    public String updateFeedback(Long id, FeedbackRequestDTO feedbackRequestDTO) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        modelMapper.map(feedbackRequestDTO,feedback);
        feedbackRepository.save(feedback);

        return "Feedback updated successfully";
    }

    @Transactional
    @Override
    public String deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedbackRepository.delete(feedback);

        return "Feedback deleted successfully";
    }
}
