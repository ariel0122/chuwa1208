package com.example.item.repository;

import com.example.item.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository
        extends MongoRepository<Item, String>,
        ItemRepositoryCustom {
}
