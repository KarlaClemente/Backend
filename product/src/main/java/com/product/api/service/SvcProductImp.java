package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.commons.dto.ApiResponse;
import com.product.commons.mapper.MapperProduct;
import com.product.exception.ApiException;

@Service
public class SvcProductImp implements SvcProduct{
	
	@Autowired
	RepoProduct repo;

	@Autowired
    RepoProductImage repoImg;
	
	@Autowired
	MapperProduct mapper;

	@Value("${app.upload.dir}")
    private String uploadDir;

	@Override
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		try {
			List<Product> products = repo.findAll();
			return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los productos de la base de datos");
		}
	}

	@Override
	public ResponseEntity<DtoProductOut> getProduct(Integer id) {
		try {
			validateProductId(id);
			DtoProductOut product = repo.getProduct(id);
            if (product == null ) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
            }
            // obtener todas las imágenes del producto como Base64 ?
            List<String> imagesBase64 = readProductImageFiles(id);
            product.setImages(imagesBase64);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener el producto de la base de datos");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> createProduct(DtoProductIn in) {
		try {
			Product product = mapper.fromDto(in);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido registrado"), HttpStatus.CREATED);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear el nuevo producto en la base de datos");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> updateProduct(Integer id, DtoProductIn in) {
		try {
			validateProductId(id);
			Product product = mapper.fromDto(id, in);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido actualizado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto en la base de datos");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> enableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(1);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido activado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al activar el producto en la base de datos");
		}
	}

	@Override
	public ResponseEntity<ApiResponse> disableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(0);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido desactivado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al desactivar el producto en la base de datos");
		}
	}
	
	private void validateProductId(Integer id) {
		try {
			if(repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error de comunicación con la base de datos");
		}
	}

	private List<String> readProductImageFiles(Integer product_id) {
        try {
            List<ProductImage> productImages = repoImg.findByProductId(product_id);
            if(productImages == null || productImages.isEmpty()) {
                return Collections.emptyList();
            }
            List<String> result = new ArrayList<>();
            for (ProductImage image : productImages) {
                String imageUrl = image.getImage();
                // Si la URL comienza con "/" la eliminamos para obtener la ruta relativa
                if (imageUrl != null && imageUrl.startsWith("/")) {
                        imageUrl = imageUrl.substring(1);
                }
                // Construir el Path
                Path imagePath = Paths.get(uploadDir, imageUrl);
                // Verifica que el archivo exista
                if (!Files.exists(imagePath)) {
                    continue; // Saltamos la imagen y venmos la siguiente (si hay)  
                }
                // Leer los bytes de la imagen y codificarlos a Base64
                byte[] imageBytes = Files.readAllBytes(imagePath);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                result.add(base64Image);
            } 
            return result;
        } catch (DataAccessException e) {
            //throw new DbAccessException("Error", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al acceder a la base de datos");
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }

}
