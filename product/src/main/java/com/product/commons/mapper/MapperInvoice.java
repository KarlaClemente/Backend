package com.product.commons.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.product.api.dto.DtoInvoiceList;
import com.product.api.entity.Invoice;

@Service
public class MapperInvoice {
	
	public List<DtoInvoiceList> toDtoList(List<Invoice> invoices) {
		List<DtoInvoiceList> dtoInvoices = new ArrayList<>();
		
		for (Invoice invoice : invoices) {
			
			DtoInvoiceList dtoInvoice = new DtoInvoiceList(
		            invoice.getInvoice_id(),
		            invoice.getUser_id(),
					invoice.getAddress_id(),
		            invoice.getCreated_at(),
		            invoice.getSubtotal(),
		            invoice.getTaxes(),
		            invoice.getTotal(),
					invoice.getCouponCode(),
					invoice.getDiscountApplied()
		        );
			dtoInvoices.add(dtoInvoice);
		}
         
         return dtoInvoices;
    }
}