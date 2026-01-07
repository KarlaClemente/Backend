package com.product.api.dto.out;

import java.util.List;


public class DtoProductOut {
    
    private Integer product_id;
    private String gtin;
    private String product;
    private String description;
    private Float price;
    private Integer stock;
    private Integer category_id;
    private String category;
    private Integer status;
    
    private List<String> images;

    public DtoProductOut(Integer product_id, String gtin, String product, String description, Float price, Integer stock, Integer category_id, String category, Integer status) {
        this.product_id = product_id;
        this.gtin = gtin;
        this.product = product;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category_id = category_id;
        this.category = category;
        this.status = status;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory() { 
        return category; 
    }

    public void setCategory(String category) { 
        this.category = category; 
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getImages() { 
        return images; 
    }

    public void setImages(List<String> images) { 
        this.images = images; 
    }
}

