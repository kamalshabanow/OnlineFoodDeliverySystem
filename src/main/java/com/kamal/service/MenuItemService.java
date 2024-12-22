package com.kamal.service;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;

import java.util.List;

public interface MenuItemService {

    //CRUD
    List<MenuItemResponseDTO> getAllMenuItems();
    MenuItemResponseDTO getMenuItemById(Long id);
    String addMenuItem(MenuItemRequestDTO menuItemRequestDTO);
    String updateMenuItem(Long id,MenuItemRequestDTO menuItemRequestDTO);
    String deleteMenuItem(Long id);


    //Search and Filtering Methods
    List<MenuItemResponseDTO> getMenuItemsByPriceRange(Double minPrice, Double maxPrice);
    List<MenuItemResponseDTO> getAvailableMenuItems();
    List<MenuItemResponseDTO> searchMenuItemsByName(String name);


    //Business Logic Methods
    String toggleMenuItemAvailability(Long itemId);
    String updateMenuItemPricesByPercentage(List<Long> itemIds, Double percentage);
    String copyMenuItems(Long sourceRestaurantId, Long targetRestaurantId);


    //Analytical Methods
    List<MenuItemResponseDTO> getMostPopularItems(Long restaurantId);
    List<MenuItemResponseDTO> getMenuItemsSortedByPrice(boolean ascending);
    Double getAverageMenuItemPrice(Long restaurantId); //average menu item price for a restaurant


    //Validation and Utility Methods
    boolean isMenuItemExists(Long restaurantId);
    boolean isValidImageUrl(String imageUrl);


    //Special Feature Methods
//    List<MenuItemResponseDTO> getDiscountedMenuItems();
    List<MenuItemResponseDTO> getRecommendedMenuItems(Long userId);  //based on user preferences

}
