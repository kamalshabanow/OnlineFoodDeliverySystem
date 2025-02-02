package com.kamal.service.impl;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.model.entity.MenuItem;
import com.kamal.model.entity.Restaurant;
import com.kamal.repository.MenuItemRepository;
import com.kamal.repository.RestaurantRepository;
import com.kamal.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
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
        modelMapper.map(menuItemRequestDTO, menuItem);
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


    //Search and Filtering Methods

    @Override
    public List<MenuItemResponseDTO> getMenuItemsByPriceRange(Double minPrice, Double maxPrice) {
        if (minPrice == null || maxPrice == null || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }

        List<MenuItem> menuItems = menuItemRepository.findByPriceBetween(minPrice, maxPrice);

        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemResponseDTO> getAvailableMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findByIsAvailableTrue();

        return menuItems.stream()
                .map(item -> modelMapper.map(item, MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemResponseDTO> searchMenuItemsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search name cannot be empty");
        }

        List<MenuItem> menuItems = menuItemRepository.findByNameContainingIgnoreCase(name.trim());

        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    //Business Logic Methods

    @Override
    public String toggleMenuItemAvailability(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuItem.setAvailable(!menuItem.isAvailable());
        menuItemRepository.save(menuItem);

        return menuItem.isAvailable() ? "Menu item is now available" : "Menu item is now unavailable";
    }

    @Transactional
    @Override
    public String updateMenuItemPricesByPercentage(List<Long> itemIds, Double percentage) {
        if (percentage == null || percentage <= -100) {
            throw new IllegalArgumentException("Invalid percentage value");
        }

        List<MenuItem> menuItems = menuItemRepository.findAllById(itemIds);

        menuItems.forEach(menuItem -> {
            double newPrice = menuItem.getPrice() * (1 + percentage / 100);
            menuItem.setPrice((double) (Math.round(newPrice * 100.0) / 100));
        });

        menuItemRepository.saveAll(menuItems);

        return "Prices updated successfully";
    }

    @Override
    public String copyMenuItems(Long sourceRestaurantId, Long targetRestaurantId) {
        Restaurant source = restaurantRepository.findById(sourceRestaurantId)
                .orElseThrow(() -> new RuntimeException("Source restaurant not found"));

        Restaurant target = restaurantRepository.findById(targetRestaurantId)
                .orElseThrow(() -> new RuntimeException("Target restaurant not found"));

        List<MenuItem> sourceMenuItems = source.getMenuItems();
        sourceMenuItems.stream()
                .map(menuItem -> {
                    MenuItem newItem = new MenuItem();
                    newItem.setName(menuItem.getName());
                    newItem.setDescription(menuItem.getDescription());
                    newItem.setPrice(menuItem.getPrice());
                    newItem.setImageUrl(menuItem.getImageUrl());
                    newItem.setRestaurant(target);
                    return newItem;
                })
                .collect(Collectors.toList());

        menuItemRepository.saveAll(sourceMenuItems);
        return "Menu items copied successfully";
    }


    //Analytical Methods

    @Override
    public List<MenuItemResponseDTO> getMostPopularItems(Long restaurantId) {
        List<MenuItem> popularMenuItems = menuItemRepository.findPopularMenuItems(restaurantId);

        return popularMenuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemResponseDTO> getMenuItemsSortedByPrice(boolean ascending) {
        List<MenuItem> sortedItems = ascending ? menuItemRepository.findAllByOrderByPriceAsc() :
                menuItemRepository.findAllByOrderByPriceDesc();

        return sortedItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageMenuItemPrice(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getMenuItems().stream()
                .mapToDouble(MenuItem::getPrice)
                .average()
                .orElse(0.0);

    }

    //Validation Methods

    @Override
    public boolean isMenuItemExists(Long restaurantId) {
        return menuItemRepository.existsById(restaurantId);
    }

    private static final String IMAGE_URL_REGEX = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*\\.(jpg|jpeg|png|gif|bmp|webp|svg)$";


    @Override
    public boolean isValidImageUrl(String imageUrl) {
        if(imageUrl == null || imageUrl.isEmpty()){
            return false;
        }

        Pattern pattern = Pattern.compile(IMAGE_URL_REGEX,Pattern.CASE_INSENSITIVE);

        return pattern.matcher(imageUrl).matches();
    }

    //Special Methods

//    @Override
//    public List<MenuItemResponseDTO> getDiscountedMenuItems() {
//        //Your discount logic here
//        return List.of();
//    }

    @Override
    public List<MenuItemResponseDTO> getRecommendedMenuItems(Long userId) {
        List<MenuItem> availableMenuItems = menuItemRepository.findByIsAvailableTrue();

        List<MenuItem> filteredMenuItems = availableMenuItems.stream()
                .filter(menuItem -> menuItem.getRestaurant().isOpen())
                .toList();

        filteredMenuItems.sort((item1, item2) ->
                Double.compare(item2.getRestaurant().getRating(),item1.getRestaurant().getRating()));

        return filteredMenuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem,MenuItemResponseDTO.class))
                .collect(Collectors.toList());

    }
}
