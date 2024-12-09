package com.kamal.service;

import com.kamal.dto.request.RestaurantRequestDTO;
import com.kamal.dto.response.RestaurantResponseDTO;
import com.kamal.entity.Restaurant;
import com.kamal.repository.RestaurantRepository;
import com.kamal.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;


    @Test
    void testGetAllRestaurants() {

        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        RestaurantResponseDTO responseDTO = RestaurantResponseDTO.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();


        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));
        when(modelMapper.map(restaurant,RestaurantResponseDTO.class)).thenReturn(responseDTO);

        List<RestaurantResponseDTO> result = restaurantService.getAllRestaurants();


        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals("Restaurant",result.get(0).getName());

        verify(restaurantRepository).findAll();
        verify(modelMapper).map(restaurant,RestaurantResponseDTO.class);

    }


    @Test
    void testGetRestaurantById() {

        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        RestaurantResponseDTO responseDTO = RestaurantResponseDTO.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(modelMapper.map(restaurant,RestaurantResponseDTO.class)).thenReturn(responseDTO);

        RestaurantResponseDTO result = restaurantService.getRestaurantById(1L);

        assertNotNull(result);
        assertEquals("Restaurant",result.getName());
        verify(restaurantRepository).findById(1L);
        verify(modelMapper).map(restaurant,RestaurantResponseDTO.class);




    }

    @Test
    void testCreateRestaurant() {

        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        RestaurantRequestDTO requestDTO = RestaurantRequestDTO.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        when(modelMapper.map(requestDTO,Restaurant.class)).thenReturn(restaurant);

        String result = restaurantService.createRestaurant(requestDTO);

        assertEquals("Restaurant created successfully",result);
        verify(restaurantRepository).save(restaurant);

    }

    @Test
    void testUpdateRestaurant(){


        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        RestaurantRequestDTO requestDTO = RestaurantRequestDTO.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        String result = restaurantService.updateRestaurant(1L, requestDTO);

        assertEquals("Restaurant updated successfully",result);

        verify(restaurantRepository).findById(1L);
        verify(restaurantRepository).save(restaurant);

    }


    @Test
    void testDeleteRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Baku")
                .phoneNumber("+994553849876")
                .cuisineType("Italian")
                .build();

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        String result = restaurantService.deleteRestaurant(1L);

        assertEquals("Restaurant deleted successfully",result);

        verify(restaurantRepository).findById(1L);
        verify(restaurantRepository).delete(restaurant);
    }

}
