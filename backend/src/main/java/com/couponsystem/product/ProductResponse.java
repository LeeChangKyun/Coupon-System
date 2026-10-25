package com.couponsystem.product;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String category,
        long price,
        int stockQuantity
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
