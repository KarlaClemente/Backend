package com.product.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Coupon;

@Repository
public interface RepoCoupon extends JpaRepository<Coupon, Integer> {
    
    @Query(value = "SELECT * FROM coupon WHERE code = :code AND is_active = true", nativeQuery = true)
    Coupon findByCode(@Param("code") String code);
    
    @Query(value = "SELECT * FROM coupon WHERE code = :code", nativeQuery = true)
    Coupon findByCodeIgnoreActive(@Param("code") String code);
    
    @Query(value = "SELECT * FROM coupon WHERE is_active = true", nativeQuery = true)
    List<Coupon> findAllActive();
    
    @Query(value = "SELECT * FROM coupon WHERE expiration_date >= :now AND start_date <= :now AND is_active = true", nativeQuery = true)
    List<Coupon> findValidCoupons(@Param("now") LocalDateTime now);
    
    @Query(value = "SELECT * FROM coupon WHERE code = :code AND is_active = true AND expiration_date >= :now AND start_date <= :now", nativeQuery = true)
    Coupon findValidByCode(@Param("code") String code, @Param("now") LocalDateTime now);
}