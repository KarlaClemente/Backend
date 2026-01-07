package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.DtoAddress;
import com.product.api.entity.Address;
import com.product.api.repository.RepoAddress;
import com.product.commons.util.JwtDecoder;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcAddressImp implements SvcAddress {
    
    @Autowired
    private RepoAddress repoAddress;
    
    @Autowired
    private JwtDecoder jwtDecoder;
    
    @Override
    public List<Address> getAddresses() {
        try {
            Integer userId = jwtDecoder.getUserId();
            return repoAddress.findByUserId(userId);
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public Address getAddress(Integer addressId) {
        try {
            Integer userId = jwtDecoder.getUserId();
            Address address = repoAddress.findByUserIdAndAddressId(userId, addressId);
            if (address == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Dirección no encontrada");
            }
            return address;
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public ApiResponse createAddress(DtoAddress dtoAddress) {
        try {
            Integer userId = jwtDecoder.getUserId();
            Address address = new Address(userId, dtoAddress.getAddressName(), dtoAddress.getStreet(), dtoAddress.getNumber(), dtoAddress.getNeighborhood(), dtoAddress.getCity(), dtoAddress.getState(), dtoAddress.getPostalCode(), dtoAddress.getCountry(), dtoAddress.getPhone(), dtoAddress.getRecipientName());
            repoAddress.save(address);
            return new ApiResponse("Dirección creada exitosamente");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public ApiResponse updateAddress(Integer addressId, DtoAddress dtoAddress) {
        try {
            Integer userId = jwtDecoder.getUserId();
            Address address = repoAddress.findByUserIdAndAddressId(userId, addressId);
            if (address == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Dirección no encontrada");
            }
            
            address.setAddressName(dtoAddress.getAddressName());
            address.setStreet(dtoAddress.getStreet());
            address.setNumber(dtoAddress.getNumber());
            address.setNeighborhood(dtoAddress.getNeighborhood());
            address.setCity(dtoAddress.getCity());
            address.setState(dtoAddress.getState());
            address.setPostalCode(dtoAddress.getPostalCode());
            address.setCountry(dtoAddress.getCountry());
            address.setPhone(dtoAddress.getPhone());
            address.setRecipientName(dtoAddress.getRecipientName());
            
            repoAddress.save(address);
            return new ApiResponse("Dirección actualizada exitosamente");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
    
    @Override
    public ApiResponse deleteAddress(Integer addressId) {
        try {
            Integer userId = jwtDecoder.getUserId();
            Address address = repoAddress.findByUserIdAndAddressId(userId, addressId);
            if (address == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Dirección no encontrada");
            }
            
            repoAddress.delete(address);
            return new ApiResponse("Dirección eliminada exitosamente");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
}