package com.example.bookapp.src.repository.orderitem;

import com.example.bookapp.src.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
