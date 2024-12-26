package com.kamal.service;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.dto.response.RestaurantResponseDTO;

import java.util.List;

public interface RestaurantService {


    //CRUD
    List<RestaurantResponseDTO> getAllRestaurants();

    RestaurantResponseDTO getRestaurantById(Long id);

    String createRestaurant(RestaurantRequestDTO restaurantRequestDTO);

    String updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO);

    String deleteRestaurant(Long id);

    //Additional methods
    List<RestaurantResponseDTO> getRestaurantByCuisineType(String cuisineType);

    List<RestaurantResponseDTO> getOpenRestaurants();

    List<RestaurantResponseDTO> getRestaurantsAboveRating(Double minRating);

    List<RestaurantResponseDTO> searchRestaurantsByName(String name);

    String toggleRestaurantStatus(Long id);

    List<MenuItemResponseDTO> getRestaurantMenuItems(Long restaurantId);

    List<FeedbackResponseDTO> getRestaurantFeedbacks(Long restaurantId);

    List<RestaurantResponseDTO> getTopRatedRestaurants(int limit);

    Long getTotalRestaurantCount();
}
