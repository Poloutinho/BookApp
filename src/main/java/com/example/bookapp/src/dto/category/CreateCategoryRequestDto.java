package com.example.bookapp.src.dto.category;

import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequestDto(@NotNull String name,
                                        String description
) {
}
