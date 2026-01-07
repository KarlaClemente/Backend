package com.product.api.dto;

public class DtoInvoiceList {
	
	private Integer id;
	
	private Integer user_id;

	private Integer address_id;
		
	private String created_at;
	
	private Double subtotal;
	
	private Double taxes;
	
	private Double total;

	private String couponCode;

    private Double discountApplied;
	
	public DtoInvoiceList() {
		
	}

	public DtoInvoiceList(Integer id, Integer user_id, Integer address_id, String created_at, Double subtotal, Double taxes, Double total, String couponCode, Double discountApplied) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.address_id = address_id;
		this.created_at = created_at;
		this.subtotal = subtotal;
		this.taxes = taxes;
		this.total = total;
		this.couponCode = couponCode;
		this.discountApplied = discountApplied;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
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

	public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(Double discountApplied) {
        this.discountApplied = discountApplied;
    }
}