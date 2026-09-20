package com.couponsystem.coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CouponEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int totalQuantity;

    private int issuedQuantity;

    public CouponEvent(String name, String description, int totalQuantity) {
        this.name = name;
        this.description = description;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = 0;
    }

    public void createCouponEvent() {
        this.issuedQuantity++;
    }
}
