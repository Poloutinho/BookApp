//package com.example.bookapp.src.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//import java.math.BigDecimal;
//
//@Entity
//@Data
//@Table(name = "order_items")
//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "book_id", nullable = false)
//    private Book book;
//
//    @Column(nullable = false)
//    private Long quantity;
//
//    @Column(nullable = false)
//    private BigDecimal price;
//}

