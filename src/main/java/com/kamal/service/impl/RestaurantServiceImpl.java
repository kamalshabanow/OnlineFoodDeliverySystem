package com.kamal.service.impl;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.FeedbackResponseDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.dto.response.RestaurantResponseDTO;
import com.kamal.model.entity.Restaurant;
import com.kamal.repository.RestaurantRepository;
import com.kamal.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;


    //CRUD

    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();

        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Transactional
    @Override
    public String createRestaurant(RestaurantRequestDTO restaurantRequestDTO) {
        Restaurant restaurant = modelMapper.map(restaurantRequestDTO, Restaurant.class);
        restaurant.setOpen(true);
        restaurantRepository.save(restaurant);

        return "Restaurant created successfully";
    }

    @Transactional
    @Override
    public String updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        modelMapper.map(restaurantRequestDTO,restaurant);
        restaurantRepository.save(restaurant);

        return "Restaurant updated successfully";
    }

    @Transactional
    @Override
    public String deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        restaurantRepository.delete(restaurant);

        return "Restaurant deleted successfully";
    }


    //Search and Filter Methods

    @Override
    public List<RestaurantResponseDTO> getRestaurantByCuisineType(String cuisineType) {
        List<Restaurant> restaurants = restaurantRepository
                .findByCuisineTypeIgnoreCase(cuisineType);

        return  restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant,RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponseDTO> getOpenRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findByIsOpenTrue();

        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant,RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponseDTO> getRestaurantsAboveRating(Double minRating) {
        List<Restaurant> restaurants = restaurantRepository
                .findByRatingGreaterThanEqual(minRating);

        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant,RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponseDTO> searchRestaurantsByName(String name) {
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(name);

        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant,RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    //Business Logic Methods

    @Transactional
    @Override
    public String toggleRestaurantStatus(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);

        return restaurant.isOpen() ? "Restaurant Opened" : "Restaurant closed";
    }

    @Override
    public List<MenuItemResponseDTO> getRestaurantMenuItems(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getMenuItems().stream()
                .map(menuItem -> modelMapper.map(menuItem,MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponseDTO> getRestaurantFeedbacks(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getFeedbacks().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackResponseDTO.class))
                .collect(Collectors.toList());
    }


    //Analytical Methods

    @Override
    public List<RestaurantResponseDTO> getTopRatedRestaurants(int limit) {
        List<Restaurant> topRestaurants = restaurantRepository
                .findTop10ByOrderByRatingDesc(PageRequest.of(0,limit));


        return topRestaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant,RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalRestaurantCount() {
        return restaurantRepository.count();
    }
}
