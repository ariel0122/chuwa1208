package com.example.order.repository;

import com.example.order.entity.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository
        extends CassandraRepository<Order, UUID> {

    @Query("UPDATE orders SET status='CANCELLED' WHERE id=?0 IF status='CREATED'")
    boolean cancelIfCreated(UUID id);

    @Query("SELECT * FROM orders WHERE userId=?0 ALLOW FILTERING")
    List<Order> findByUserId(Long userId);
}
