package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.commons.dto.ApiResponse;
import com.product.exception.ApiException;

@Service
public class SvcCtegoryImp implements SvcCategory {
    @Autowired
    RepoCategory repoCategory;

    @Override
    public List<Category> findAll() {
        try {
            return repoCategory.findAll();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar categorías en la base de datos");
        }
    }
    
    @Override
    public List<Category> findActive() {
    	try {
            return repoCategory.findActive();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las categorías activas en la base de datos");
        }
    }
    
    @Override
	public ApiResponse create(DtoCategoryIn in) {
        if (repoCategory.existsByCategory(in.getCategory()))
            throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
        if (repoCategory.existsByTag(in.getTag()))
            throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
    	try {
    		repoCategory.create(in.getCategory(), in.getTag());
    		return new ApiResponse("La categoría ha sido registrada");
    	} catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la nueva categoría en la base de datos");
    	}
	}
    
    @Override
	public ApiResponse update(DtoCategoryIn in, Integer id) {
        if (repoCategory.findById(id).isEmpty()) {
	        throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
    	repoCategory.update(id, in.getCategory(), in.getTag());
        return new ApiResponse("La región ha sido actualizada");
    }
    
    @Override
	public ApiResponse enable(Integer id) {
    	validateCategoryId(id);
        repoCategory.setStatus(id,1);
        return new ApiResponse("La categoría ha sido activada");
    }
    
    @Override
	public ApiResponse disable(Integer id) {
    	validateCategoryId(id);
        repoCategory.setStatus(id,0);
        return new ApiResponse("La categoría ha sido desactivada");
    }

    private void validateCategoryId(Integer id) {
        if (repoCategory.findById(id).isEmpty()) {
	        throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
    }
}
