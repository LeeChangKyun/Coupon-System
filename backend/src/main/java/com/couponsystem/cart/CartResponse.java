package com.couponsystem.cart;

import java.util.List;

public record CartResponse(
        List<CartItemResponse> items,
        long totalPrice
) {
    public static CartResponse from(List<CartItemResponse> items) {
        long totalPrice = items.stream().mapToLong(CartItemResponse::lineTotal).sum();
        return new CartResponse(items, totalPrice);
    }
}
