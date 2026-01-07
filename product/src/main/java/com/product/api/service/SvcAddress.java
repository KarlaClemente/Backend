package com.product.api.service;

import java.util.List;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.DtoAddress;
import com.product.api.entity.Address;

public interface SvcAddress {
    List<Address> getAddresses();
    Address getAddress(Integer addressId);
    ApiResponse createAddress(DtoAddress dtoAddress);
    ApiResponse updateAddress(Integer addressId, DtoAddress dtoAddress);
    ApiResponse deleteAddress(Integer addressId);
}