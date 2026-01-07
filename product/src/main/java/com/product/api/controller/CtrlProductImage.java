package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImage;
import com.product.commons.dto.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/{id}/image")
public class CtrlProductImage {

	@Autowired
    SvcProductImage svc;
	
	@GetMapping
    public ResponseEntity<List<ProductImage>> getProductImages(@PathVariable("id") Integer productId) {
        return ResponseEntity.ok(svc.getProductImages(productId));
    }
	
    @PostMapping
    public ResponseEntity<ApiResponse> createProductImage(@Valid @RequestBody DtoProductImageIn in){
    	return ResponseEntity.ok(svc.upload(in));
    }
    
    @DeleteMapping("/{product-image-id}")
    public ResponseEntity<ApiResponse> deleteProductImage(@PathVariable("product-image-id") Integer id) {
    	return ResponseEntity.ok(svc.deleteProductImage(id));
    }
}
