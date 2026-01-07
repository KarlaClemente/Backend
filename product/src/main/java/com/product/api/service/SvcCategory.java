package com.product.api.service;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.commons.dto.ApiResponse;

import java.util.List;

public interface SvcCategory {
	
	public List<Category> findAll();
	public List<Category> findActive();
	public ApiResponse create(DtoCategoryIn in);
	public ApiResponse update(DtoCategoryIn in, Integer id);
	public ApiResponse enable(Integer id);
	public ApiResponse disable(Integer id);
} 
