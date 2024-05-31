package com.capstoneproject.itemservice.service;

import com.capstoneproject.itemservice.dto.ItemLotRequest;
import com.capstoneproject.itemservice.dto.ItemLotResponse;
import com.capstoneproject.itemservice.dto.ItemRequest;
import com.capstoneproject.itemservice.dto.ItemResponse;
import com.capstoneproject.itemservice.model.Item;
import com.capstoneproject.itemservice.model.ItemLot;
import com.capstoneproject.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;


    public void createItem(ItemRequest itemRequest) {
        Item item = Item.builder()
                .brandId(itemRequest.getBrandId())
                .productId(itemRequest.getProductId())
                .sku(itemRequest.getSku())
                .itemName(itemRequest.getItemName())
                .mrp(itemRequest.getMrp())
                .unit(itemRequest.getUnit())
                .dimension(itemRequest.getDimension())
                .weight(itemRequest.getWeight())
                .mpn(itemRequest.getMpn())
                .isbn(itemRequest.getIsbn())
                .upc(itemRequest.getUpc())
                .ean(itemRequest.getEan())
                .isActive(itemRequest.getIsActive())
                .minRecomStock(itemRequest.getMinRecomStock())
                .build();
        item.setCreatedBy("ram"); //need to implement login and name should come from userid
        item.setCreatedAt(LocalDateTime.now());
        itemRepository.save(item);
        log.info("Item {} is saved", item.getId());
    }

    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();

        return items.stream().map(this::mapToItemResponse).toList();
    }

    public List<ItemResponse> getAllActiveItems() {
        List<Item> items = itemRepository.getAllByIsActiveTrue();

        return items.stream().map(this::mapToItemResponse).toList();
    }

    public ItemResponse getItemById(Long id) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        return itemOpt.isPresent() ? itemOpt.map(this::mapToItemResponse).get() : null;
    }

    @Transactional(readOnly = true)
    public boolean isInStock(Long itemId, Integer quantity) {
        return itemRepository.existsByIdAndQuantityIsGreaterThanEqual(itemId, quantity);
    }

    public void updateItem(Long id, ItemRequest itemRequest) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.map(opt ->
                    Item.builder()
                            .id(opt.getId())
                            .brandId(Optional.ofNullable(itemRequest.getBrandId()).orElse(opt.getBrandId()))
                            .productId(Optional.ofNullable(itemRequest.getProductId()).orElse(opt.getProductId()))
                            .itemName(Optional.ofNullable(itemRequest.getItemName()).orElse(opt.getItemName()))
                            .sku(Optional.ofNullable(itemRequest.getSku()).orElse(opt.getSku()))
                            .mrp(Optional.ofNullable(itemRequest.getMrp()).orElse(opt.getMrp()))
                            .unit(Optional.ofNullable(itemRequest.getUnit()).orElse(opt.getUnit()))
                            .dimension(itemRequest.getDimension() != null ? itemRequest.getDimension() : opt.getDimension())
                            .weight(Optional.ofNullable(itemRequest.getWeight()).orElse(opt.getWeight()))
                            .mpn(Optional.ofNullable(itemRequest.getMpn()).orElse(opt.getMpn()))
                            .isbn(Optional.ofNullable(itemRequest.getIsbn()).orElse(opt.getIsbn()))
                            .upc(Optional.ofNullable(itemRequest.getUpc()).orElse(opt.getUpc()))
                            .ean(Optional.ofNullable(itemRequest.getEan()).orElse(opt.getEan()))
                            .isActive(Optional.ofNullable(itemRequest.getIsActive()).orElse(opt.getIsActive()))
                            .minRecomStock(Optional.ofNullable(itemRequest.getMinRecomStock()).orElse(opt.getMinRecomStock()))
                            .createdBy(opt.getCreatedBy())
                            .updatedBy("shyam") //change the name with the login credential
                            .createdAt(opt.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build()
            ).get();
            itemRepository.saveAndFlush(item);
            log.info("Item {} is updated", item.getId());
        }
    }

    private ItemResponse mapToItemResponse(Item item) {
        List<ItemLotResponse> itemLotResponses = item.getItemLots() != null ?
                item.getItemLots().stream().map(this::mapToItemLotResponse).toList() :
                Collections.emptyList();
        return ItemResponse.builder()
                .id(item.getId())
                .brandId(item.getBrandId())
                .productId(item.getProductId())
                .itemName(item.getItemName())
                .sku(item.getSku())
                .mrp(item.getMrp())
                .unit(item.getUnit())
                .dimension(item.getDimension())
                .weight(item.getWeight())
                .mpn(item.getMpn())
                .isbn(item.getIsbn())
                .upc(item.getUpc())
                .ean(item.getEan())
                .isActive(item.getIsActive())
                .minRecomStock(item.getMinRecomStock())
                .itemLots(itemLotResponses)
                .build();
    }
    private ItemLotResponse mapToItemLotResponse(ItemLot itemLot) {
        return ItemLotResponse.builder()
                .id(itemLot.getId())
                .quantity(itemLot.getQuantity())
                .sold(itemLot.getSold())
                .returnableItem(itemLot.isReturnableItem())
                .expireDate(itemLot.getExpireDate())
                .available(itemLot.getAvailable())
                .defective(itemLot.getDefective())
                .defectiveCustReturned(itemLot.getDefectiveCustReturned())
                .build();
    }


    public ItemLot mapToItemLot(ItemLotRequest itemLotRequest, Item item) {
        ItemLot itemLot = new ItemLot();

        itemLot.setReturnableItem(itemLotRequest.isReturnableItem());
        itemLot.setItem(item);
        itemLot.setSold(itemLotRequest.getSold());
        itemLot.setQuantity(itemLotRequest.getQuantity());
        itemLot.setAvailable(itemLotRequest.getAvailable());
        itemLot.setDefective(itemLotRequest.getDefective());
        itemLot.setDefectiveCustReturned(itemLotRequest.getDefectiveCustReturned());
        itemLot.setCreatedAt(LocalDateTime.now());
        itemLot.setUpdatedAt(LocalDateTime.now());
        itemLot.setCreatedBy("System");
        itemLot.setUpdatedBy("System");
        return itemLot;
    }
    public void createItemLot(Long id, ItemRequest itemRequest) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            List<ItemLot> itemLots = item.getItemLots();
                   List<ItemLot> tempLot = itemRequest.getItemLots().stream()
                    .map(x -> mapToItemLot(x, item))
                    .toList();
                   itemLots.addAll(tempLot);
            // Set the OrderItem list to the Order object
            item.setItemLots(itemLots);
            itemRepository.saveAndFlush(item);
            log.info("Item {} is updated", item.getId());
        }
    }
}
