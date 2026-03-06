package com.example.item.repository;

import com.example.item.entity.Item;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean reduceStockAtomic(String id, Integer quantity) {

        Query query = new Query();
        query.addCriteria(
                Criteria.where("_id").is(id)
                        .and("inventory").gte(quantity)
        );

        Update update = new Update().inc("inventory", -quantity);

        UpdateResult result =
                mongoTemplate.updateFirst(query, update, Item.class);

        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean increaseStockAtomic(String id, Integer quantity) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update().inc("inventory", quantity);

        UpdateResult result =
                mongoTemplate.updateFirst(query, update, Item.class);

        return result.getModifiedCount() > 0;
    }
}
