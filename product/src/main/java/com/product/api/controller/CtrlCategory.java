package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

/**
 * Clase CtrlProduct encargada de definir los endpoints relacionados con la clase Catgeory.
 */
@RestController
@RequestMapping("/category") 
public class CtrlCategory {
    @Autowired SvcCategory svc;
    
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return svc.getCategories();
    }
}
