package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoCartItemIn;
import com.product.api.dto.out.DtoCartItemOut;
import com.product.api.service.SvcCartItem;
import com.product.commons.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart-item")
@Tag(name="Cart Item", description = "Endpoints para la gestión del carrito de compras")
public class CtrlCartItem {
	
	@Autowired
	SvcCartItem svc;
	
	@PostMapping
	@Operation(summary = "Agrega un producto al carrito", description = "Agrega un producto al carrito de compras o aumenta la cantidad si ya existe")
	public ResponseEntity<ApiResponse> addCartItem(@Valid @RequestBody DtoCartItemIn in){
		return svc.addCartItem(in);
	}
	
	@GetMapping
	@Operation(summary = "Consulta todos los productos del carrito", description = "Regresa una lista con todos los productos del carrito guardados en la BD")
	public ResponseEntity<List<DtoCartItemOut>> getCartItems(){
		return svc.getCartItems();
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un artículo del carrito", description = "Elimina de forma permanente un producto en particular del carrito")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Integer id){
		return svc.deleteCartItem(id);
	}
	
	@DeleteMapping
	@Operation(summary = "Eliminar todos los artículos en el carrito", description = "Elimina de forma permanente todos los productos del carrito")
	public ResponseEntity<ApiResponse> deleteCartItem(){
		return svc.deleteCartItem();
	}
}
