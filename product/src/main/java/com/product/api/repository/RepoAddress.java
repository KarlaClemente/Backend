package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Address;

@Repository
public interface RepoAddress extends JpaRepository<Address, Integer> {
    
    @Query(value = "SELECT * FROM address WHERE user_id = :userId", nativeQuery = true)
    List<Address> findByUserId(@Param("userId") Integer userId);
    
    @Query(value = "SELECT * FROM address WHERE user_id = :userId AND address_id = :addressId", nativeQuery = true)
    Address findByUserIdAndAddressId(@Param("userId") Integer userId, @Param("addressId") Integer addressId);
}