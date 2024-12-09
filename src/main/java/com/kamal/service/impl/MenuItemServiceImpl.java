package com.kamal.service.impl;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.entity.MenuItem;
import com.kamal.entity.Restaurant;
import com.kamal.repository.MenuItemRepository;
import com.kamal.repository.RestaurantRepository;
import com.kamal.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MenuItemResponseDTO> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();

        return modelMapper.map(menuItem, MenuItemResponseDTO.class);
    }

    @Transactional
    @Override
    public String addMenuItem(MenuItemRequestDTO menuItemRequestDTO) {
        Restaurant restaurant = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem menuItem = modelMapper.map(menuItemRequestDTO, MenuItem.class);
        menuItemRepository.save(menuItem);
        menuItem.setRestaurant(restaurant);

        return "Menu item added successfully";
    }

    @Transactional
    @Override
    public String updateMenuItem(Long id, MenuItemRequestDTO menuItemRequestDTO) {

        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();
        modelMapper.map(menuItemRequestDTO,menuItem);
        menuItemRepository.save(menuItem);

        return "Menu item updated successfully";
    }

    @Transactional
    @Override
    public String deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();
        menuItemRepository.delete(menuItem);

        return "Menu item deleted successfully";
    }
}
