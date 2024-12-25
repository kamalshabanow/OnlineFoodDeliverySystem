package com.kamal.service;

import com.kamal.dto.request.FeedbackRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.dto.response.RestaurantResponseDTO;

import java.util.List;

public interface FeedbackService {

    //CRUD
    List<FeedbackResponseDTO> getAllFeedbacks();
    FeedbackResponseDTO getFeedbackById(Long id);
    String createFeedback(FeedbackRequestDTO feedbackRequestDTO);
    String updateFeedback(Long id, FeedbackRequestDTO feedbackRequestDTO);
    String deleteFeedback(Long id);

    //Additional Methods

    Double getAverageRestaurantRating(Long restaurantId);
    List<FeedbackResponseDTO> getLatestRestaurantReviews(Long restaurantId, int limit);

    List<FeedbackResponseDTO> getUserFeedbacks(Long userId);
    boolean hasUserReviewedOrder(Long userId, Long orderId);

    List<RestaurantResponseDTO> getTrendingRestaurantsByFeedback(int days, int limit);
    List<RestaurantResponseDTO> getMostImprovedRestaurants(int days, int limit);
    List<FeedbackResponseDTO> getRecentNegativeFeedbacks(Double ratingThreshold, int days);
}
