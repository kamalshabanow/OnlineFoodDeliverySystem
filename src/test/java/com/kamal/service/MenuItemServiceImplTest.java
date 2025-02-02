package com.kamal.service;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.model.entity.MenuItem;
import com.kamal.model.entity.Restaurant;
import com.kamal.repository.MenuItemRepository;
import com.kamal.repository.RestaurantRepository;
import com.kamal.service.impl.MenuItemServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;


    @Test
    void testGetAllMenuItems(){
        MenuItem menuItem = MenuItem.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        MenuItemResponseDTO responseDTO = MenuItemResponseDTO.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(menuItem));
        when(modelMapper.map(menuItem, MenuItemResponseDTO.class)).thenReturn(responseDTO);

        List<MenuItemResponseDTO> result = menuItemService.getAllMenuItems();

        assertNotNull(result);

        assertEquals(1,result.size());
        assertEquals(responseDTO.getName(),result.get(0).getName());
        assertEquals(responseDTO.getDescription(),result.get(0).getDescription());
        assertEquals(responseDTO.getPrice(),result.get(0).getPrice());

        verify(menuItemRepository,times(1)).findAll();
        verify(modelMapper,times(1)).map(menuItem, MenuItemResponseDTO.class);
    }

    @Test
    void testGetMenuItemById() {

        MenuItem menuItem = MenuItem.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        MenuItemResponseDTO responseDTO = MenuItemResponseDTO.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(modelMapper.map(menuItem, MenuItemResponseDTO.class)).thenReturn(responseDTO);

        MenuItemResponseDTO result = menuItemService.getMenuItemById(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getName(),result.getName());
        assertEquals(responseDTO.getDescription(),result.getDescription());
        assertEquals(responseDTO.getPrice(),result.getPrice());

        verify(menuItemRepository,times(1)).findById(1L);
        verify(modelMapper,times(1)).map(menuItem, MenuItemResponseDTO.class);
    }

    @Test
    void testAddMenuItem() {

        MenuItem menuItem = MenuItem.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        MenuItemRequestDTO requestDTO = MenuItemRequestDTO.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .restaurantId(1L)
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("Milano")
                .address("Italy")
                .build();


        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(modelMapper.map(requestDTO,MenuItem.class)).thenReturn(menuItem);

        String result = menuItemService.addMenuItem(requestDTO);

        assertNotNull(result);
        assertEquals("Menu item added successfully",result);

        verify(menuItemRepository,times(1)).save(menuItem);
        verify(modelMapper,times(1)).map(requestDTO,MenuItem.class);
    }

    @Test
    void testUpdateMenuItem() {

        MenuItem menuItem = MenuItem.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        MenuItemRequestDTO requestDTO = MenuItemRequestDTO.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        String result = menuItemService.updateMenuItem(1L, requestDTO);

        assertEquals("Menu item updated successfully",result);

        verify(menuItemRepository,times(1)).findById(1L);
        verify(menuItemRepository,times(1)).save(menuItem);
        verify(modelMapper,times(1)).map(requestDTO,menuItem);

    }

    @Test
    void  testDeleteMenuItem() {
        MenuItem menuItem = MenuItem.builder()
                .name("Pizza")
                .description("Chicken BBQ")
                .price(10.0)
                .build();

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        String result = menuItemService.deleteMenuItem(1L);

        assertEquals("Menu item deleted successfully",result);

        verify(menuItemRepository,times(1)).findById(1L);
        verify(menuItemRepository,times(1)).delete(menuItem);
    }
}
