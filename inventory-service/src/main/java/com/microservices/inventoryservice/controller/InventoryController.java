package com.microservices.inventoryservice.controller;

import com.microservices.inventoryservice.dto.InventoryGetDto;
import com.microservices.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inventory")
@RequiredArgsConstructor
public class InventoryController {


    private final InventoryService inventoryService;

//    http://localhost:8082/api/intentory/Firsttry
//    http://localhost:8082/api/intentory?sku_code=First&sku_code=Second

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryGetDto> isInStock(@RequestParam List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }

}
