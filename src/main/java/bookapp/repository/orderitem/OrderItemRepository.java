package bookapp.repository.orderitem;

import bookapp.dto.order.OrderItemDto;
import bookapp.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItemDto> findByIdAndOrderId(Long orderId, Long itemId);
}
