package com.product.api.service;

import com.product.api.entity.Category;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface SvcCategory {
    public ResponseEntity<List<Category>> getCategories();
} 
