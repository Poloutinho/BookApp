//package com.example.bookapp.src.service;
//
//import com.example.bookapp.src.dto.order.CreateOrderRequestDto;
//import com.example.bookapp.src.dto.order.OrderDto;
//import com.example.bookapp.src.dto.orderItem.OrderItemDto;
//import com.example.bookapp.src.exception.EntityNotFoundException;
//import com.example.bookapp.src.mapper.OrderMapper;
//import com.example.bookapp.src.model.Order;
//import com.example.bookapp.src.model.OrderItem;
//import com.example.bookapp.src.model.User;
//import com.example.bookapp.src.repository.order.OrderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//public class OrderServiceImpl implements OrderService {
//    private final OrderRepository orderRepository;
//    private final OrderMapper orderMapper;
//
//    @Override
//    public List<OrderDto> findAll(Pageable pageable) {
//        return orderRepository.findAll().stream()
//                .map(orderMapper::toDto)
//                .toList();
//    }
//
//    @Override
//    public OrderDto save(CreateOrderRequestDto orderRequestDto, Authentication authentication) {
//        User user = (User)authentication.getPrincipal();
//        Order order = orderRepository.findById(user.getId()).orElseGet(() -> {
//            Order newOrder = new Order();
//            newOrder.setUser(user);
//            newOrder.getOrderDate();
//            newOrder.setStatus(Order.Status.PENDING);
//            newOrder.setShippingAddress(orderRequestDto.shippingAddress());
//            orderRepository.save(newOrder);
//            return newOrder;
//        });
//        order.setUser(user);
//        order.getOrderDate();
//        order.setStatus(Order.Status.PENDING);
//        order.setShippingAddress(orderRequestDto.shippingAddress());
//        orderRepository.save(order);
//        Order savedOrder = orderRepository.save(order);
//        return orderMapper.toDto(savedOrder);
//    }
//
//    @Override
//    public OrderDto findById(Long id) {
//        Order order = orderRepository.findById(id).orElseThrow(() ->
//                new EntityNotFoundException("Order not found with id: " + id));
//        return orderMapper.toDto(order);
//    }
//
//    @Override
//    public List<OrderItemDto> getOrderItems(Long orderId) {
//        OrderDto orderDto = findById(orderId);
//        return orderDto.getOrderItems().stream()
//                .map(orderItemMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
//        OrderDto orderDto = findById(orderId);
//        OrderItem orderItem = orderDto.getOrderItems().stream()
//                .filter(item -> item.getId().equals(itemId))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));
//        return orderItemMapper.toDto(orderItem);
//    }
//
//    @Override
//    public OrderDto updateOrderStatus(Long orderId, Order.Status newStatus) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() ->
//                new EntityNotFoundException("Order not found with id: " + orderId));
//        order.setStatus(newStatus);
//        order = orderRepository.save(order);
//        return orderMapper.toDto(order);
//    }
//}
