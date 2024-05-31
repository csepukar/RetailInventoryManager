package com.capstoneproject.orderservice.client;

import com.capstoneproject.orderservice.dto.ItemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "item", url = "${inventory.url}")  //
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/api/item/createItemLot/{id}")
    void createItemLot(@PathVariable Long id, @RequestBody ItemRequest itemRequest);
}
