package com.example.item.controller;

import com.example.item.entity.ApiResponse;
import com.example.item.entity.Item;
import com.example.item.exception.BizException;
import com.example.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public ApiResponse<List<Item>> list() {
        return ApiResponse.success(service.listAll());
    }

    @PostMapping
    public ApiResponse<Item> create(@Valid @RequestBody Item item, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            throw new BizException(403, "Admin only");
        }

        return ApiResponse.success(service.create(item));
    }

    @GetMapping("/{id}")
    public ApiResponse<Item> get(@PathVariable String id) {
        return ApiResponse.success(service.get(id));
    }

    @PostMapping("/reduce")
    public ApiResponse<Item> reduce(@RequestParam String id,
                                    @RequestParam Integer quantity) {

        return ApiResponse.success(
                service.reduceStock(id, quantity)
        );
    }

    @PostMapping("/increase")
    public ApiResponse<Item> increase(@RequestParam String id,
                                      @RequestParam Integer quantity) {

        return ApiResponse.success(
                service.increaseStock(id, quantity)
        );
    }
}

