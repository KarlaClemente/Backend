package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.dto.out.DtoCartItemOut;
import com.product.api.entity.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface RepoCartItem extends JpaRepository<CartItem, Integer>{
	
	@Query(value = "SELECT c.cart_item_id, p.gtin, p.product, p.price, c.quantity "
			+ "FROM cart_item c "
			+ "INNER JOIN product p ON p.gtin = c.gtin "
			+ "WHERE c.user_id = :user_id", nativeQuery = true)
	List<DtoCartItemOut> getCartItems(@Param("user_id") Integer user_id);
	CartItem findByUserIdAndGtin(Integer userId, String gtin);
	boolean existsByCartItemIdAndUserId(Integer cartItemId, Integer userId);
	@Transactional
    @Modifying
    int deleteByUserId(Integer userId);
}
