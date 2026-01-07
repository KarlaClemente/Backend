package com.product.api.service;

import java.util.List;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.commons.dto.ApiResponse;

public interface SvcProductImage {

	public List<ProductImage> getProductImages(Integer productId);
	public ApiResponse upload(DtoProductImageIn in);
	public ApiResponse deleteProductImage(Integer id);
}
