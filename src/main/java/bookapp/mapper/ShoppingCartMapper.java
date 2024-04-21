package bookapp.mapper;

import bookapp.config.MapperConfig;
import bookapp.dto.shoppingcart.CartItemRequestUpdateDto;
import bookapp.dto.shoppingcart.CartItemDto;
import bookapp.dto.shoppingcart.CartItemRequestDto;
import bookapp.dto.shoppingcart.ShoppingCartDto;
import bookapp.model.CartItem;
import bookapp.model.ShoppingCart;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    default List<CartItemDto> mapCartItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toCartItemDto)
                .toList();
    }

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    CartItem toModel(CartItemRequestDto requestDto);

    void update(@MappingTarget CartItem cartItem, CartItemRequestUpdateDto requestUpdateDto);
}
