package com.capstoneproject.itemservice.controller;

import com.capstoneproject.itemservice.dto.*;
import com.capstoneproject.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody ItemRequest itemRequest) {
        itemService.createItem(itemRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        itemService.updateItem(id,itemRequest);
    }

    // Get an item by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

}
