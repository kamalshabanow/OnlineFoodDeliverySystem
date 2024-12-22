package com.kamal.controller;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        return ResponseEntity.ok(menuItemService.addMenuItem(menuItemRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItemRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.deleteMenuItem(id));
    }

    //Search and Filtering Endpoints

    @GetMapping("/price-range")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuItemsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {

        return ResponseEntity.ok(menuItemService.getMenuItemsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/available")
    public ResponseEntity<List<MenuItemResponseDTO>> getAvailableMenuItems() {
        return ResponseEntity.ok(menuItemService.getAvailableMenuItems());
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<List<MenuItemResponseDTO>> searchMenuItemsByName(@RequestParam String name) {
        return ResponseEntity.ok(menuItemService.searchMenuItemsByName(name));
    }

    //Business Logic Endpoints

    @PutMapping("{id}/toggle-availability")
    public ResponseEntity<String> toggleMenuItemAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.toggleMenuItemAvailability(id));
    }

    @PutMapping("/update-prices")
    public ResponseEntity<String> updateMenuItemPricesByPercentage(
            @RequestBody List<Long> itemIds,
            @RequestParam Double percentage ) {

        return ResponseEntity.ok(menuItemService.updateMenuItemPricesByPercentage(itemIds,percentage));
    }

    @PostMapping("/copy-menu-items")
    public ResponseEntity<String> copyMenuItems(
            @RequestParam Long sourceRestaurantId,
            @RequestParam Long targetRestaurantId ) {

        return ResponseEntity.ok(menuItemService.copyMenuItems(sourceRestaurantId, targetRestaurantId));
    }

    //Analytical Endpoints

    @GetMapping("/popular-items")
    public ResponseEntity<List<MenuItemResponseDTO>> getMostPopularItems(
            @RequestParam Long restaurantId ) {

        return ResponseEntity.ok(menuItemService.getMostPopularItems(restaurantId));
    }

    @GetMapping("sorted-by-price")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuItemsSortedByPrice(
            @RequestParam boolean ascending ) {
        return ResponseEntity.ok(menuItemService.getMenuItemsSortedByPrice(ascending));
    }

    @GetMapping("/average-item-price")
    public ResponseEntity<Double> getAverageMenuItemPrice(@RequestParam Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getAverageMenuItemPrice(restaurantId));
    }

    //Validation Endpoints

    @GetMapping("/exists")
    public ResponseEntity<Boolean> isMenuItemExists(@RequestParam Long restaurantId) {
        return ResponseEntity.ok(menuItemService.isMenuItemExists(restaurantId));
    }

    //Special Endpoints

    @GetMapping("/recommended-items")
    public ResponseEntity<List<MenuItemResponseDTO>> getRecommendedMenuItems(@RequestParam Long userId){
        return ResponseEntity.ok(menuItemService.getRecommendedMenuItems(userId));
    }



}
