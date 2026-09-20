package com.couponsystem.coupon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyIssuedException.class)
    public ResponseEntity<String> handleAlreadyIssued(AlreadyIssuedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(CouponSoldOutException.class)
    public ResponseEntity<String> handleCouponSoldOut(CouponSoldOutException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NotExistCouponEventException.class)
    public ResponseEntity<String> notExistCouponEvent(NotExistCouponEventException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
