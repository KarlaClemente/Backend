package com.product.api.dto.in;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DtoCouponIn {
    
    @NotBlank(message = "El código del cupón es requerido")
    @Size(min = 5, max = 20, message = "El código debe tener entre 5 y 20 caracteres")
    private String code;
    
    @NotNull(message = "El valor del descuento es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor del descuento debe ser mayor a 0")
    private Double discountValue;
    
    @NotNull(message = "El monto mínimo de compra es requerido")
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto mínimo debe ser mayor o igual a 0")
    private Double minPurchaseAmount;
    
    @NotNull(message = "El máximo de usos es requerido")
    @Min(value = 1, message = "El máximo de usos debe ser al menos 1")
    private Integer maxUses;
    
    @NotNull(message = "La fecha de inicio es requerida")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    
    @NotNull(message = "La fecha de expiración es requerida")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;
    
    private Boolean isActive;
    
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
}