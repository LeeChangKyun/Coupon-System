package com.couponsystem.product;

public class NotExistsProductException extends RuntimeException {
    public NotExistsProductException(String message) {
        super(message);
    }
}
