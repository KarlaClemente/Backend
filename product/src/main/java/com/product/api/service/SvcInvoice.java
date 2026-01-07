package com.product.api.service;

import java.util.List;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.DtoInvoiceList;
import com.product.api.entity.Invoice;

public interface SvcInvoice {

	public List<DtoInvoiceList> findAll();
	public Invoice findById(Integer id);
	public ApiResponse create();
	public ApiResponse checkout(Integer addressId, String couponCode);
}