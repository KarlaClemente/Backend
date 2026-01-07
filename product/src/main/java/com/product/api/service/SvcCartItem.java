package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.dto.in.DtoCartItemIn;
import com.product.api.dto.out.DtoCartItemOut;
import com.product.commons.dto.ApiResponse;

public interface SvcCartItem {

	public ResponseEntity<ApiResponse> addCartItem(DtoCartItemIn in);
	public ResponseEntity<List<DtoCartItemOut>> getCartItems();
	public ResponseEntity<ApiResponse> deleteCartItem(Integer id);
	public ResponseEntity<ApiResponse> deleteCartItem();
}
