package com.couponsystem.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequest(
        @NotNull Long userId,
        @NotNull Long productId,
        @Positive int quantity
) {
}
