package com.product.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
    /**
     * Constructor para crear una excepción, pasándole como parámetros el estatus http del error y la descripción del error
     * @param status el estatus http del error
     * @param message una representación en cadena con la descripción del error.
     */
	public ApiException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

    /* Constructor que usa la descripción del status como mensaje por defecto */
    public ApiException(HttpStatus status) {
        super(status.getReasonPhrase()); 
        this.status = status;
    }

    /**
     * Regresa el estatus http del error.
     * @return el estatus http del error
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Método encargado cambiar el estatus http.
     * @param status el estatus http.
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Regresa el serialVersionUID de la clase.
     * @return el serialVersionUID de la clase.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}

