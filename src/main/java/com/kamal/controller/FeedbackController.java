package com.kamal.controller;

import com.kamal.dto.request.FeedbackRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.dto.response.RestaurantResponseDTO;
import com.kamal.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;


    // CRUD Endpoints

    @GetMapping()
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDTO> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        return new ResponseEntity<>(feedbackService.createFeedback(feedbackRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFeedback(@PathVariable Long id, @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, feedbackRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.deleteFeedback(id));
    }

    //Additional Endpoints

    @GetMapping("/avg-rating-feedback/{restaurantId}")
    public ResponseEntity<Double> getAverageRestaurantRatingByFeedbacks(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(feedbackService.getAverageRestaurantRatingByFeedbacks(restaurantId));
    }

    @GetMapping("/latest-restaurant-reviews/{restaurantId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getLatestRestaurantReviews(
            @PathVariable Long restaurantId,
            @RequestParam int limit ) {

        return ResponseEntity.ok(feedbackService.getLatestRestaurantReviews(restaurantId,limit));
    }

    @GetMapping("/user-feedbacks/{userId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getUserFeedbacks(
            @PathVariable Long userId ){

        return ResponseEntity.ok(feedbackService.getUserFeedbacks(userId));
    }

    @PostMapping("/has-user-reviewed/{userId}/{orderId}")
    public ResponseEntity<Boolean> hasUserReviewedOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId ){

        return ResponseEntity.ok(feedbackService.hasUserReviewedOrder(userId,orderId));
    }

    @GetMapping("/trending-restaurants-byfeedbacks")
    public ResponseEntity<List<RestaurantResponseDTO>> getTrendingRestaurantsByFeedback(
            @RequestParam int days,
            @RequestParam int limit ){

        return ResponseEntity.ok(feedbackService.getTrendingRestaurantsByFeedback(days,limit));
    }

    @GetMapping("/recent-negative-feedbacks")
    public ResponseEntity<List<FeedbackResponseDTO>> getRecentNegativeFeedbacks(
            @RequestParam Double ratingThreshold,
            @RequestParam int days ){

        return ResponseEntity.ok(feedbackService.getRecentNegativeFeedbacks(ratingThreshold,days));
    }
}
