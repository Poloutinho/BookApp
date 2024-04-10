package com.example.bookapp.src.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(@NotNull String title,
                                   @NotNull String author,
                                   @NotNull String isbn,
                                   @NotNull @Min(0) BigDecimal price,
                                   String description,
                                   String coverImage,
                                   List<Long> categories
) {
}
