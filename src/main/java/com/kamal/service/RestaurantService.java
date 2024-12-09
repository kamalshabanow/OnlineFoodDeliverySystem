package com.kamal.service;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.RestaurantResponseDTO;

import java.util.List;

public interface RestaurantService {

    List<RestaurantResponseDTO> getAllRestaurants();
    RestaurantResponseDTO getRestaurantById(Long id);
    String createRestaurant(RestaurantRequestDTO restaurantRequestDTO);
    String updateRestaurant(Long id,RestaurantRequestDTO restaurantRequestDTO);
    String deleteRestaurant(Long id);
}
