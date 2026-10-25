package com.couponsystem.cart;

import com.couponsystem.product.Product;

public record CartItemResponse(
        Long productId,
        String productName,
        long price,
        int quantity,
        long lineTotal
) {
    public static CartItemResponse of(Product product, int quantity) {
        return new CartItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity,
                product.getPrice() * quantity
        );
    }
}
