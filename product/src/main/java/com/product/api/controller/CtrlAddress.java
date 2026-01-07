package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.DtoAddress;
import com.product.api.entity.Address;
import com.product.api.service.SvcAddress;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/addresses")
public class CtrlAddress {
    
    @Autowired
    private SvcAddress svcAddress;
    
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        return new ResponseEntity<>(svcAddress.getAddresses(), HttpStatus.OK);
    }
    
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer addressId) {
        return new ResponseEntity<>(svcAddress.getAddress(addressId), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> createAddress(@Valid @RequestBody DtoAddress dtoAddress, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(svcAddress.createAddress(dtoAddress), HttpStatus.CREATED);
    }
    
    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse> updateAddress(@PathVariable Integer addressId, @Valid @RequestBody DtoAddress dtoAddress, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(svcAddress.updateAddress(addressId, dtoAddress), HttpStatus.OK);
    }
    
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer addressId) {
        return new ResponseEntity<>(svcAddress.deleteAddress(addressId), HttpStatus.OK);
    }
}