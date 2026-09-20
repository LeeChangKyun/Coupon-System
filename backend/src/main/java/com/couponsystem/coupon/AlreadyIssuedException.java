package com.couponsystem.coupon;

public class AlreadyIssuedException extends RuntimeException {
    public AlreadyIssuedException(String message) {
        super(message);
    }
}
