package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Payment;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    int findByUser(User user);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.user.userId = :userId")
    BigDecimal findTotalAmountByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(p.couponCount) FROM Payment p WHERE p.user.userId = :userId")
    int findTotalCouponCountByUserId(Long userId);

    @Query("""
    SELECT
	    COUNT(pm.couponCount)
    FROM
	    Payment pm
    LEFT OUTER JOIN Pass ps
	    ON pm.paymentId = ps.payment.paymentId
    WHERE
	    pm.user.userId = :userId
    AND
        ps.used = false
""")
    int findAvailableCouponCountByUserId(Long userId);
}
