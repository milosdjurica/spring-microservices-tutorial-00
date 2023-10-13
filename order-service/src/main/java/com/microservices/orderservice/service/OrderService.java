package com.microservices.orderservice.service;


import com.microservices.orderservice.dto.InventoryGetDto;
import com.microservices.orderservice.dto.OrderCreateDto;
import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    public void placeOrder(OrderCreateDto orderCreateDto) {

//        CREATING NEW ORDER
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

//        Setting up the List<OrderLineItems> from Dto to Order object
        List<OrderLineItems> orderLineItems = orderCreateDto.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);


//        Get all skuCodes
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode).toList();


//        Call InventoryService and place order if Product is in stock
//        get() -> because in InventoryService method is @Get method
        InventoryGetDto[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryGetDto[].class)
                .block();


//        converting InventoryGetDto objects to boolean depending on if they can be found in stock
//        if EVERY OBJECT IS AVAILABLE then this will be true
        boolean allProductsInStock = Arrays
                .stream(inventoryResponseArray)
                .allMatch(InventoryGetDto::isInStock);

//        make order ONLY IF EVERY item from order is available!!!
        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later!");
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }

}
