package com.couponsystem.coupon;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"coupon_event_id", "user_id"}))
@Getter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponEventId;

    private Long userId;

    private LocalDateTime issuedAt;

    public Coupon(Long couponEventId, Long userId) {
        this.couponEventId = couponEventId;
        this.userId = userId;
        this.issuedAt = LocalDateTime.now();
    }
}
