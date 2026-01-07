package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_item_id")
	private Integer invoice_item_id;
	
	@Column(name = "invoice_id")
	private Integer invoice_id;
	
	@Column(name = "gtin")
	private String gtin;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "unit_price")
	private Double unit_price;

	@Column(name = "subtotal")
	private Double subtotal;

	@Column(name = "taxes")
	private Double taxes;

	@Column(name = "total")
	private Double total;

	@Column(name = "status", nullable = false)
    private int status;
	
	public InvoiceItem() {}

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

	/**
     * Regresa el estatus de una categoría.
     * @return 0 o 1 dependiendo del estatus de la categoría.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Define el estatus de una categoría.
     * @param status el nuevo estatus de una categoría.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}