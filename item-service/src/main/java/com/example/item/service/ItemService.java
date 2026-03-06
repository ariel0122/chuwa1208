package com.example.item.service;

import com.example.item.entity.Item;
import com.example.item.exception.BizException;
import com.example.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public List<Item> listAll() {
        return repository.findAll();
    }

    public Item create(Item item) {
        return repository.save(item);
    }

    public Item get(String id) {

        Item item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        return item;
    }

    public Item reduceStock(String id, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new BizException(400, "Invalid quantity");
        }

        boolean success = repository.reduceStockAtomic(id, quantity);

        if (!success) {
            throw new BizException(400, "Not enough stock");
        }

        return repository.findById(id)
                .orElseThrow(() -> new BizException(404, "Item not found"));
    }


    public Item increaseStock(String id, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new BizException(400, "Invalid quantity");
        }

        boolean success = repository.increaseStockAtomic(id, quantity);

        if (!success) {
            throw new BizException(400, "Item not found");
        }

        return repository.findById(id)
                .orElseThrow(() -> new BizException(404, "Item not found"));
    }
}
