package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoCartItemIn;
import com.product.api.dto.out.DtoCartItemOut;
import com.product.api.entity.CartItem;
import com.product.api.entity.Product;
import com.product.api.repository.RepoCartItem;
import com.product.api.repository.RepoProduct;
import com.product.commons.dto.ApiResponse;
import com.product.commons.util.JwtDecoder;
import com.product.exception.ApiException;

@Service
public class SvcCartItemImp implements SvcCartItem{
	
	@Autowired
	RepoCartItem repoCartItem;
	
	@Autowired
	RepoProduct repoProduct;
	
	@Autowired
	private JwtDecoder jwtDecoder;
	
	@Override
	public ResponseEntity<ApiResponse> addCartItem(DtoCartItemIn in){
		try {
			String gtin = in.getGtin();
			Product product = repoProduct.findByGtin(gtin);
			if (product == null) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El producto con GTIN " + gtin + " no fue encontrado");
			}
			Integer quantity = in.getQuantity();
			Integer userId = jwtDecoder.getUserId();
			CartItem cartItem = repoCartItem.findByUserIdAndGtin(userId, gtin);
			if (cartItem == null) {
				verifyStock(quantity, product);
				cartItem = new CartItem();
				cartItem.setUserId(userId);
				cartItem.setGtin(gtin);
				cartItem.setQuantity(quantity);
				repoCartItem.save(cartItem);
				return new ResponseEntity<>(new ApiResponse("El producto ha sido agregado al carrito de compras"), HttpStatus.CREATED);
			} else {
				Integer newQuantity = cartItem.getQuantity() + quantity;
				verifyStock(newQuantity, product);
				cartItem.setQuantity(newQuantity);
				repoCartItem.save(cartItem);
				return new ResponseEntity<>(new ApiResponse("Ha sido actualizada la cantidad en el carrito"), HttpStatus.OK);
			}
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar el producto en el carrito de compras");
		}
	}
	
	private void verifyStock(Integer quantity, Product product) {
		if (product.getStock() < quantity) {
			throw new ApiException(HttpStatus.CONFLICT, "No hay suficiente stock disponible para la cantidad solicitada");
		}
	}
	
	@Override
	public ResponseEntity<List<DtoCartItemOut>> getCartItems(){
		try {
			Integer userId = jwtDecoder.getUserId();
			return new ResponseEntity<>(repoCartItem.getCartItems(userId), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los productos del carrito de compras");
		}
	}
	
	@Override
	public ResponseEntity<ApiResponse> deleteCartItem(Integer id){
		try {
			if (!repoCartItem.existsByCartItemIdAndUserId(id, jwtDecoder.getUserId())) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El artículo no existe en el carrito.");
			}
			CartItem cartItem = repoCartItem.findById(id).get();
			repoCartItem.delete(cartItem);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido eliminado del carrito"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto del carrito de compras");
		}
	}
	
	@Override
	public ResponseEntity<ApiResponse> deleteCartItem(){
		try {
			Integer userId = jwtDecoder.getUserId();
			repoCartItem.deleteByUserId(userId);
			return new ResponseEntity<>(new ApiResponse("Se han eliminado todos los productos del carrito"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar todos los productos del carrito de compras");
		}
	}
}
