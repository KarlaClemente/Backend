package com.product.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.product.api.dto.ApiResponse;
import com.product.api.dto.in.DtoCouponIn;
import com.product.api.dto.out.DtoCouponOut;
import com.product.api.entity.Coupon;
import com.product.api.repository.RepoCoupon;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcCouponImp implements SvcCoupon {
    
    @Autowired
    private RepoCoupon repoCoupon;
    
    @Override
    public List<DtoCouponOut> getAllCoupons() {
        try {
            List<Coupon> coupons = repoCoupon.findAll();
            return coupons.stream()
                .map(this::convertToDtoOut)
                .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public DtoCouponOut getCouponById(Integer couponId) {
        try {
            Coupon coupon = repoCoupon.findById(couponId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
            return convertToDtoOut(coupon);
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public DtoCouponOut getCouponByCode(String code) {
        try {
            Coupon coupon = repoCoupon.findByCodeIgnoreActive(code);
            if (coupon == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Cupón no encontrado");
            }
            return convertToDtoOut(coupon);
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public ApiResponse createCoupon(DtoCouponIn dtoCoupon) {
        try {
            Coupon existingCoupon = repoCoupon.findByCodeIgnoreActive(dtoCoupon.getCode());
            if (existingCoupon != null) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "El código del cupón ya existe");
            }            
            validateCouponDates(dtoCoupon.getStartDate(), dtoCoupon.getExpirationDate());
            Coupon coupon = new Coupon();
            coupon.setCode(dtoCoupon.getCode().toUpperCase());
            coupon.setDiscountValue(dtoCoupon.getDiscountValue());
            coupon.setMinPurchaseAmount(dtoCoupon.getMinPurchaseAmount());
            coupon.setMaxUses(dtoCoupon.getMaxUses());
            coupon.setStartDate(dtoCoupon.getStartDate());
            coupon.setExpirationDate(dtoCoupon.getExpirationDate());
            repoCoupon.save(coupon);
            return new ApiResponse("Cupón creado exitosamente");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public ApiResponse updateCoupon(Integer couponId, DtoCouponIn dtoCoupon) {
        try {
            Coupon coupon = repoCoupon.findById(couponId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
            validateCouponDates(dtoCoupon.getStartDate(), dtoCoupon.getExpirationDate());
            if (!coupon.getCode().equalsIgnoreCase(dtoCoupon.getCode())) {
                Coupon existingWithCode = repoCoupon.findByCodeIgnoreActive(dtoCoupon.getCode());
                if (existingWithCode != null && !existingWithCode.getCouponId().equals(couponId)) {
                    throw new ApiException(HttpStatus.BAD_REQUEST, "El código del cupón ya está en uso");
                }
                coupon.setCode(dtoCoupon.getCode().toUpperCase());
            }
            coupon.setDiscountValue(dtoCoupon.getDiscountValue());
            coupon.setMinPurchaseAmount(dtoCoupon.getMinPurchaseAmount());
            coupon.setMaxUses(dtoCoupon.getMaxUses());
            coupon.setStartDate(dtoCoupon.getStartDate());
            coupon.setExpirationDate(dtoCoupon.getExpirationDate());
            if (dtoCoupon.getIsActive() != null) {
                coupon.setIsActive(dtoCoupon.getIsActive());
            }
            repoCoupon.save(coupon);
            return new ApiResponse("Cupón actualizado exitosamente");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public ApiResponse deleteCoupon(Integer couponId) {
        try {
            Coupon coupon = repoCoupon.findById(couponId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
            repoCoupon.delete(coupon);
            return new ApiResponse("Cupón eliminado exitosamente");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public Double validateAndApplyCoupon(String couponCode, Double currentTotal) {
        try {
            if (couponCode == null || couponCode.trim().isEmpty()) {
                return currentTotal; 
            }
            LocalDateTime now = LocalDateTime.now();
            Coupon coupon = repoCoupon.findValidByCode(couponCode.toUpperCase(), now);           
            if (coupon == null) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Cupón no válido, expirado o inactivo");
            }
            /* Validamos el monto mínimo de compa */
            if (currentTotal < coupon.getMinPurchaseAmount()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, String.format("El monto mínimo para usar este cupón es: $%.2f", coupon.getMinPurchaseAmount()));
            }
            /* Validamos que no se haya utilizado el cupón mas de lo debido */
            if (coupon.getTotalUses() >= coupon.getMaxUses()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Este cupón ha alcanzado su límite de usos");
            }
            Double discountedTotal = currentTotal - coupon.getDiscountValue();
            /* Solución temporal a valores negativos */
            return Math.max(discountedTotal, 0.0);
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public void incrementCouponUse(String couponCode) {
        try {
            if (couponCode == null || couponCode.trim().isEmpty()) {
                return; 
            }
            Coupon coupon = repoCoupon.findByCode(couponCode.toUpperCase());
            if (coupon != null) {
                coupon.setTotalUses(coupon.getTotalUses() + 1);
                if (coupon.getTotalUses() >= coupon.getMaxUses()) {
                    coupon.setIsActive(false);
                }
                repoCoupon.save(coupon);
            }
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    private void validateCouponDates(LocalDateTime startDate, LocalDateTime expirationDate) {
        if (expirationDate.isBefore(LocalDateTime.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La fecha de expiración debe ser futura");
        }
        if (expirationDate.isBefore(startDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, 
                "La fecha de expiración debe ser posterior a la fecha de inicio");
        }
    }
    
    private DtoCouponOut convertToDtoOut(Coupon coupon) {
        DtoCouponOut dto = new DtoCouponOut();
        dto.setCouponId(coupon.getCouponId());
        dto.setCode(coupon.getCode());
        dto.setDiscountValue(coupon.getDiscountValue());
        dto.setMinPurchaseAmount(coupon.getMinPurchaseAmount());
        dto.setTotalUses(coupon.getTotalUses());
        dto.setMaxUses(coupon.getMaxUses());
        dto.setStartDate(coupon.getStartDate());
        dto.setExpirationDate(coupon.getExpirationDate());
        dto.setIsActive(coupon.getIsActive());
        dto.setCreatedAt(coupon.getCreatedAt());
        return dto;
    }
}