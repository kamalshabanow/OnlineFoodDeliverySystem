package com.kamal.service;

import com.kamal.dto.request.FeedbackRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;

import java.util.List;

public interface FeedbackService {

    List<FeedbackResponseDTO> getAllFeedbacks();
    FeedbackResponseDTO getFeedbackById(Long id);
    String createFeedback(FeedbackRequestDTO feedbackRequestDTO);
    String updateFeedback(Long id, FeedbackRequestDTO feedbackRequestDTO);
    String deleteFeedback(Long id);
}
