package com.product.api.service;

import java.util.List;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.in.DtoCouponIn;
import com.product.api.dto.out.DtoCouponOut;

public interface SvcCoupon {
    List<DtoCouponOut> getAllCoupons();
    DtoCouponOut getCouponById(Integer couponId);
    DtoCouponOut getCouponByCode(String code);
    ApiResponse createCoupon(DtoCouponIn dtoCoupon);
    ApiResponse updateCoupon(Integer couponId, DtoCouponIn dtoCoupon);
    ApiResponse deleteCoupon(Integer couponId);
    Double validateAndApplyCoupon(String couponCode, Double currentTotal);
    void incrementCouponUse(String couponCode);
}