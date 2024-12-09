package com.kamal.service;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;

import java.util.List;

public interface MenuItemService {

    List<MenuItemResponseDTO> getAllMenuItems();
    MenuItemResponseDTO getMenuItemById(Long id);
    String addMenuItem(MenuItemRequestDTO menuItemRequestDTO);
    String updateMenuItem(Long id,MenuItemRequestDTO menuItemRequestDTO);
    String deleteMenuItem(Long id);
}
