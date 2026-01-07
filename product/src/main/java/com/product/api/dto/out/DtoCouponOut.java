package com.product.api.dto.out;

import java.time.LocalDateTime;

public class DtoCouponOut {
    private Integer couponId;
    private String code;
    private Double discountValue;
    private Double minPurchaseAmount;
    private Integer totalUses;
    private Integer maxUses;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    
    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(Double minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Integer getTotalUses() {
        return totalUses;
    }

    public void setTotalUses(Integer totalUses) {
        this.totalUses = totalUses;
    }

    public Integer getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(Integer maxUses) {
        this.maxUses = maxUses;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}