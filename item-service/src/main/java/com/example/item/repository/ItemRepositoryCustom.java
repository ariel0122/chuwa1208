package com.example.item.repository;

public interface ItemRepositoryCustom {

    boolean reduceStockAtomic(String id, Integer quantity);

    boolean increaseStockAtomic(String id, Integer quantity);
}
