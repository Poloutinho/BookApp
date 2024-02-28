package com.example.bookapp.src.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBookRequestDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
}
