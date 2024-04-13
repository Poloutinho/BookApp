package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.order.CreateOrderRequestDto;
import com.example.bookapp.src.dto.order.OrderDto;
import com.example.bookapp.src.dto.order.OrderItemDto;
import com.example.bookapp.src.dto.order.OrderUpdateRequestDto;
import com.example.bookapp.src.model.Order;
import com.example.bookapp.src.model.OrderItem;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    default List<OrderItemDto> mapOrderItems(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::toOrderItemDto)
                .toList();
    }

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    Order toModel(CreateOrderRequestDto requestDto);

    void update(@MappingTarget Order order, OrderUpdateRequestDto requestUpdateDto);
}
