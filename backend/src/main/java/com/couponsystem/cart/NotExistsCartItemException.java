package com.couponsystem.cart;

public class NotExistsCartItemException extends RuntimeException {
    public NotExistsCartItemException(String message) {
        super(message);
    }
}
