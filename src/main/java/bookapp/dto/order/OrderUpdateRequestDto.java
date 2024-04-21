package bookapp.dto.order;

import bookapp.model.Order;

public record OrderUpdateRequestDto(
        Order.Status status
) {
}
