package com.kamal.controller;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.dto.response.RestaurantResponseDTO;
import com.kamal.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants(){
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long id){
        RestaurantResponseDTO restaurant = restaurantService.getRestaurantById(id);

        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO){
        return ResponseEntity.ok().body(restaurantService.createRestaurant(restaurantRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO restaurantRequestDTO){
        String result = restaurantService.updateRestaurant(id, restaurantRequestDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id){
        String result = restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok(result);
    }

    //Search and Filter Endpoints

    @GetMapping("/cuisine/{cuisineType}")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByCuisineType(@PathVariable String cuisineType) {
        List<RestaurantResponseDTO> restaurants = restaurantService.getRestaurantByCuisineType(cuisineType);

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/open")
    public ResponseEntity<List<RestaurantResponseDTO>> getOpenRestaurants() {
        List<RestaurantResponseDTO> restaurants = restaurantService.getOpenRestaurants();

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/above-rating")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsAboveRating(@RequestParam Double minRating) {
        List<RestaurantResponseDTO> restaurants = restaurantService.getRestaurantsAboveRating(minRating);

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<List<RestaurantResponseDTO>> searchRestaurantsByName(@RequestParam String name) {
        List<RestaurantResponseDTO> restaurants = restaurantService.searchRestaurantsByName(name);

        return ResponseEntity.ok(restaurants);
    }


    //Business Logic Endpoints

    @PatchMapping("/toggle-status/{id}")
    public ResponseEntity<String> toggleRestaurantStatus(@PathVariable Long id){
        String result = restaurantService.toggleRestaurantStatus(id);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/restaurant-menu-items/{id}")
    public ResponseEntity<List<MenuItemResponseDTO>> getRestaurantMenuItems(@PathVariable Long id){
        List<MenuItemResponseDTO> restaurantMenuItems = restaurantService.getRestaurantMenuItems(id);

        return ResponseEntity.ok(restaurantMenuItems);
    }

    @GetMapping("/restaurant-feedbacks/{id}")
    public ResponseEntity<List<FeedbackResponseDTO>> getRestaurantFeedbacks(@PathVariable Long id){
        List<FeedbackResponseDTO> restaurantFeedbacks = restaurantService.getRestaurantFeedbacks(id);

        return ResponseEntity.ok(restaurantFeedbacks);
    }


    //Analytical Endpoints

    @GetMapping("/top-rated-restaurants")
    public ResponseEntity<List<RestaurantResponseDTO>> getTopRatedRestaurants(@RequestParam(defaultValue = "10") int limit){
        List<RestaurantResponseDTO> topRatedRestaurants = restaurantService.getTopRatedRestaurants(limit);

        return ResponseEntity.ok(topRatedRestaurants);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalRestaurantCount() {
        Long restaurantCount = restaurantService.getTotalRestaurantCount();

        return ResponseEntity.ok(restaurantCount);
    }
}
