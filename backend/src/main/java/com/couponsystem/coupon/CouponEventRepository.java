package com.couponsystem.coupon;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ce from CouponEvent ce where ce.id = :id")
    Optional<CouponEvent> findByIdForUpdate(@Param("id") Long id);

}
