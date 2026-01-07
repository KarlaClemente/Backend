package com.product.api.dto.out;

public class DtoCartItemOut {
	
	private Integer cartItemId;
	private String gtin;
    private String product;
    private Float price;
	private Integer quantity;
	
	public DtoCartItemOut(Integer cartItemId, String gtin, String product, Float price, Integer quantity) {
		super();
		this.cartItemId = cartItemId;
		this.gtin = gtin;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Integer getCartItemId() {
		return cartItemId;
	}
	
	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = cartItemId;
	}
	
	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
