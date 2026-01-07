package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {
	
	@Id
	private Integer invoice_item_id;
	
	@JsonIgnore
	private Integer invoice_id;
	
	private String gtin;

	private Integer quantity;

	private Double unit_price;

	private Double subtotal;

	private Double taxes;

	private Double total;
	
	public InvoiceItem() {
		
	}

	public InvoiceItem(Integer invoice_item_id, Integer invoice_id, String gtin, Integer quantity, Double unit_price,
			Double subtotal, Double taxes, Double total) {
		super();
		this.invoice_item_id = invoice_item_id;
		this.invoice_id = invoice_id;
		this.gtin = gtin;
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.subtotal = subtotal;
		this.taxes = taxes;
		this.total = total;
	}

	public Integer getInvoice_item_id() {
		return invoice_item_id;
	}

	public void setInvoice_item_id(Integer invoice_item_id) {
		this.invoice_item_id = invoice_item_id;
	}

	public Integer getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}