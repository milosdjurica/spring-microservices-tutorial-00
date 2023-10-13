package com.microservices.inventoryservice;

import com.microservices.inventoryservice.model.Inventory;
import com.microservices.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);

    }


//    creating data at the program start
    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){

        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("First try");
            inventory.setQuantity(10);

            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("Second");
            inventory1.setQuantity(4);

            inventoryRepository.save(inventory);
            inventoryRepository.save(inventory1);

        };
    }

}
