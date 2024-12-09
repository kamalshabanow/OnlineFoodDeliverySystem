package com.kamal.controller;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.RestaurantResponseDTO;
import com.kamal.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantResponseDTO> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getRestaurantById(@PathVariable Long id){
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO){
        return restaurantService.createRestaurant(restaurantRequestDTO);
    }

    @PutMapping("/update/{id}")
    public String updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO restaurantRequestDTO){
        return restaurantService.updateRestaurant(id,restaurantRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }
}
