package bookapp.mapper;

import bookapp.config.MapperConfig;
import bookapp.dto.order.CreateOrderRequestDto;
import bookapp.dto.order.OrderDto;
import bookapp.dto.order.OrderItemDto;
import bookapp.dto.order.OrderUpdateRequestDto;
import bookapp.model.CartItem;
import bookapp.model.Order;
import bookapp.model.OrderItem;
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

    @Mapping(target = "price", source = "book.price")
    OrderItem cartItemToOrderItem(CartItem cartItem);

    Order toModel(CreateOrderRequestDto requestDto);

    void update(@MappingTarget Order order, OrderUpdateRequestDto requestUpdateDto);
}
