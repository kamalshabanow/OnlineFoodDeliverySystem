package com.kamal.controller;

import com.kamal.dto.request.FeedbackRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping()
    public List<FeedbackResponseDTO> getAllFeedbacks(){
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    public FeedbackResponseDTO getFeedbackById(@PathVariable Long id){
        return feedbackService.getFeedbackById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO){
        return feedbackService.createFeedback(feedbackRequestDTO);
    }

    @PutMapping("/update/{id}")
    public String updateFeedback(@PathVariable Long id, @RequestBody FeedbackRequestDTO feedbackRequestDTO){
        return  feedbackService.updateFeedback(id,feedbackRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id){
        return feedbackService.deleteFeedback(id);
    }
}
