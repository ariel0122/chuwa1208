package com.example.order.client;

import com.example.order.config.FeignConfig;
import com.example.order.entity.ApiResponse;
import com.example.order.entity.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "item-service", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface ItemClient {

    @PostMapping("/items/reduce")
    ApiResponse<Void> reduce(@RequestParam("id") String id,
                             @RequestParam("quantity") Integer quantity);

    @PostMapping("/items/increase")
    ApiResponse<Void> increase(@RequestParam("id") String id,
                  @RequestParam("quantity") Integer quantity);

    @GetMapping("/items/{id}")
    ApiResponse<ItemDTO> getItem(@PathVariable("id") String id);
}
