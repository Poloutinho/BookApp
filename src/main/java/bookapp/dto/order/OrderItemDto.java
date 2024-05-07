package bookapp.dto.order;

public record OrderItemDto(
        Long id,
        Long bookId,
        Long quantity
) {
}
