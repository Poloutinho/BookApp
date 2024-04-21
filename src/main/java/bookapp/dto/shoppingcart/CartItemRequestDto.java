package bookapp.dto.shoppingcart;

public record CartItemRequestDto(
        Long bookId,
        int quantity
) {
}
