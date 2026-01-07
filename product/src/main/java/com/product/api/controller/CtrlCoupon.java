package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.in.DtoCouponIn;
import com.product.api.dto.out.DtoCouponOut;
import com.product.api.service.SvcCoupon;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/coupons")
public class CtrlCoupon {
    
    @Autowired
    private SvcCoupon svcCoupon;
    
    @GetMapping
    public ResponseEntity<List<DtoCouponOut>> getAllCoupons() {
        return new ResponseEntity<>(svcCoupon.getAllCoupons(), HttpStatus.OK);
    }
    
    @GetMapping("/{couponId}")
    public ResponseEntity<DtoCouponOut> getCouponById(@PathVariable Integer couponId) {
        return new ResponseEntity<>(svcCoupon.getCouponById(couponId), HttpStatus.OK);
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<DtoCouponOut> getCouponByCode(@PathVariable String code) {
        return new ResponseEntity<>(svcCoupon.getCouponByCode(code), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> createCoupon(@Valid @RequestBody DtoCouponIn dtoCoupon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, 
                bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(svcCoupon.createCoupon(dtoCoupon), HttpStatus.CREATED);
    }
    
    @PutMapping("/{couponId}")
    public ResponseEntity<ApiResponse> updateCoupon(@PathVariable Integer couponId, @Valid @RequestBody DtoCouponIn dtoCoupon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, 
                bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(svcCoupon.updateCoupon(couponId, dtoCoupon), HttpStatus.OK);
    }
    
    @DeleteMapping("/{couponId}")
    public ResponseEntity<ApiResponse> deleteCoupon(@PathVariable Integer couponId) {
        return new ResponseEntity<>(svcCoupon.deleteCoupon(couponId), HttpStatus.OK);
    }
    
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateCoupon(@RequestBody CouponValidationRequest request) {
        try {
            Double discountedTotal = svcCoupon.validateAndApplyCoupon(request.getCouponCode(), request.getCurrentTotal());
            Double discount = request.getCurrentTotal() - discountedTotal;
            return new ResponseEntity<>(new ApiResponse( String.format("Cupón válido. Descuento aplicado: $%.2f. Total con descuento: $%.2f", discount, discountedTotal)), HttpStatus.OK);
        } catch (ApiException e) {
            throw e;
        }
    }
    
    public static class CouponValidationRequest {
        private String couponCode;
        private Double currentTotal;
        
        public String getCouponCode() {
            return couponCode;
        }
        
        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }
        
        public Double getCurrentTotal() {
            return currentTotal;
        }
        
        public void setCurrentTotal(Double currentTotal) {
            this.currentTotal = currentTotal;
        }
    }
}