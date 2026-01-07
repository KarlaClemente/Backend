package com.product.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DtoAddress {
    
    @NotNull(message = "El nombre de la dirección es requerido")
    @Size(min = 1, max = 50, message = "El nombre de la dirección debe tener entre 1 y 50 caracteres")
    private String addressName;
    
    @NotNull(message = "La calle es requerida")
    @Size(min = 1, max = 100, message = "La calle debe tener entre 1 y 100 caracteres")
    private String street;
    
    @NotNull(message = "El número es requerido")
    @Size(min = 1, max = 10, message = "El número debe tener entre 1 y 10 caracteres")
    private String number;
    
    @NotNull(message = "La colonia es requerida")
    @Size(min = 1, max = 50, message = "La colonia debe tener entre 1 y 50 caracteres")
    private String neighborhood;
    
    @NotNull(message = "La ciudad es requerida")
    @Size(min = 1, max = 50, message = "La ciudad debe tener entre 1 y 50 caracteres")
    private String city;
    
    @NotNull(message = "El estado es requerido")
    @Size(min = 1, max = 50, message = "El estado debe tener entre 1 y 50 caracteres")
    private String state;
    
    @NotNull(message = "El código postal es requerido")
    @Size(min = 5, max = 10, message = "El código postal debe tener entre 5 y 10 caracteres")
    private String postalCode;
    
    @NotNull(message = "El país es requerido")
    @Size(min = 1, max = 50, message = "El país debe tener entre 1 y 50 caracteres")
    private String country;
    
    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 10, max = 15, message = "El teléfono debe tener entre 10 y 15 caracteres")
    private String phone;
    
    @NotNull(message = "El nombre del destinatario es requerido")
    @Size(min = 1, max = 100, message = "El nombre del destinatario debe tener entre 1 y 100 caracteres")
    private String recipientName;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}