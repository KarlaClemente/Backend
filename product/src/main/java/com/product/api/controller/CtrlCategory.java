package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import com.product.commons.dto.ApiResponse;

import jakarta.validation.Valid;

/**
 * Clase CtrlProduct encargada de definir los endpoints relacionados con la clase Catgeory.
 */
@RestController
@RequestMapping("/category") 
public class CtrlCategory {
    @Autowired SvcCategory svc;
    
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive(){
    	return ResponseEntity.ok(svc.findActive());
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoCategoryIn in){
    	return ResponseEntity.ok(svc.create(in));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody DtoCategoryIn in, @PathVariable("id") Integer id){
    	return ResponseEntity.ok(svc.update(in, id));
    }
    
    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse> enable(@PathVariable Integer id){
    	return ResponseEntity.ok(svc.enable(id));
    }
    
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse> disable(@PathVariable Integer id){
    	return ResponseEntity.ok(svc.disable(id));
    }
}
