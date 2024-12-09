package com.kamal.controller;

import com.kamal.dto.request.MenuItemRequestDTO;
import com.kamal.dto.response.MenuItemResponseDTO;
import com.kamal.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menuItems")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public List<MenuItemResponseDTO> getAllMenuItems(){
        return  menuItemService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public MenuItemResponseDTO getMenuItemById(@PathVariable Long id){
        return menuItemService.getMenuItemById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String addMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.addMenuItem(menuItemRequestDTO);
    }

    @PutMapping("/udpate/{id}")
    public String updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.updateMenuItem(id,menuItemRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id){
        return menuItemService.deleteMenuItem(id);
    }


}
