package com.couponsystem.coupon;

public class NotExistCouponEventException extends RuntimeException {
    public NotExistCouponEventException(String message) {
        super(message);
    }
}
