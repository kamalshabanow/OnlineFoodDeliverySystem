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
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems(){
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable Long id){
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return ResponseEntity.ok(menuItemService.addMenuItem(menuItemRequestDTO));
    }

    @PutMapping("/update/{id}")
    public String updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.updateMenuItem(id,menuItemRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id){
        return menuItemService.deleteMenuItem(id);
    }


}
