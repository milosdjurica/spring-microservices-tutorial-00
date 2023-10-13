package com.microservices.orderservice.controller;


import com.microservices.orderservice.dto.OrderCreateDto;
import com.microservices.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderCreateDto orderCreateDto) {
        orderService.placeOrder(orderCreateDto);
        return "Order placed";
    }

}
