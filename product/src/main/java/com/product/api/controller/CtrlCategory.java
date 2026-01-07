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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Clase CtrlProduct encargada de definir los endpoints relacionados con la clase Catgeory.
 */
@RestController
@RequestMapping("/category") 
@Tag(name = "Category", description = "Endpoints para la gestión de categorías de productos")
public class CtrlCategory {
    @Autowired SvcCategory svc;
    
    @GetMapping
    @Operation(summary = "Consultar todas las categorías", description = "Regresa una lista con todas las categorías guardadas en la BD.")
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }
    
    @GetMapping("/active")
    @Operation(summary = "Consultar categorías activas", description = "Regresa una lista con todas las categorías que tienen estátus activo (1).")
    public ResponseEntity<List<Category>> findActive(){
    	return ResponseEntity.ok(svc.findActive());
    }
    
    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría y la guarda en la BD.")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoCategoryIn in){
    	return ResponseEntity.ok(svc.create(in));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Permite modificar los datos de una categoría existente.")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody DtoCategoryIn in, @PathVariable("id") Integer id){
    	return ResponseEntity.ok(svc.update(in, id));
    }
    
    @PatchMapping("/{id}/enable")
    @Operation(summary = "Activar categoría", description = "Cambia el estátus de una categoría de inactiva (0) a activa (1).")
    public ResponseEntity<ApiResponse> enable(@PathVariable Integer id){
    	return ResponseEntity.ok(svc.enable(id));
    }
    
    @PatchMapping("/{id}/disable")
    @Operation(summary = "Desactivar categoría",  description = "Desactiva una categoría sin eliminarla de la BD. Cambia el valor de su estatus de 1 a 0.")
    public ResponseEntity<ApiResponse> disable(@PathVariable Integer id){
    	return ResponseEntity.ok(svc.disable(id));
    }
}
