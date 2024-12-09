package com.kamal.service.impl;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.RestaurantResponseDTO;
import com.kamal.entity.Restaurant;
import com.kamal.repository.RestaurantRepository;
import com.kamal.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;


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
}
