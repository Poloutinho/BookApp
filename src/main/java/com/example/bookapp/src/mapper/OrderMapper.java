//package com.example.bookapp.src.mapper;
//
//import com.example.bookapp.src.dto.order.CreateOrderRequestDto;
//import com.example.bookapp.src.dto.order.OrderDto;
//import com.example.bookapp.src.model.Order;
//import org.mapstruct.Mapper;
//import com.example.bookapp.src.config.MapperConfig;
//import org.mapstruct.Mapping;
//
//@Mapper(config = MapperConfig.class, uses = UserMapper.class)
//public interface OrderMapper {
//    @Mapping(target = "userId", source = "user", qualifiedByName = "idFromUser")
//    OrderDto toDto(Order order);
//
//    Order toModel(CreateOrderRequestDto orderDto);
//}
