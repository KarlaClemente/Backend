package com.product.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.DtoInvoiceList;
import com.product.api.dto.out.DtoCartItemOut;
import com.product.api.entity.Address;
import com.product.api.entity.Invoice;
import com.product.api.entity.InvoiceItem;
import com.product.api.entity.Product;
import com.product.api.repository.RepoAddress;
import com.product.api.repository.RepoCartItem;
import com.product.api.repository.RepoInvoice;
import com.product.api.repository.RepoInvoiceItem;
import com.product.api.repository.RepoProduct;
import com.product.commons.mapper.MapperInvoice;
import com.product.commons.util.JwtDecoder;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import lombok.extern.slf4j.Slf4j;

@Service
public class SvcInvoiceImp implements SvcInvoice {

	public class DBAccessException extends RuntimeException {

    public DBAccessException(String message) {
        super(message);
    }

    public DBAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBAccessException() {
        super("Error al acceder a la base de datos");
    }
}
	
	@Autowired
    private RepoInvoice repo;

	@Autowired
    private RepoInvoiceItem repoInvoiceItem;

	@Autowired
    private RepoCartItem repoCartItem;

	@Autowired
	private RepoProduct repoProduct;

	@Autowired
	private RepoAddress repoAddress;

	@Autowired
    private SvcCoupon svcCoupon;
	
	@Autowired
	private JwtDecoder jwtDecoder;
	
	@Autowired
	MapperInvoice mapper;

	@Override
	public List<DtoInvoiceList> findAll() {
		try {
			if(jwtDecoder.isAdmin()) {
				return mapper.toDtoList(repo.findAll());
			}else {
				Integer user_id = jwtDecoder.getUserId();
				return mapper.toDtoList(repo.findAllByUserId(user_id));
			}
		}catch (DataAccessException e) {
	        throw new DBAccessException();
	    }
	}

	@Override
	public Invoice findById(Integer id) {
		try {
			Invoice invoice = repo.findById(id).get();
			if(!jwtDecoder.isAdmin()) {
				Integer user_id = jwtDecoder.getUserId();
				if(invoice.getUser_id() != user_id) {
					throw new ApiException(HttpStatus.FORBIDDEN, "El token no es válido para consultar esta factura");
				}
			}
			return invoice;
		}catch (DataAccessException e) {
	        throw new DBAccessException();
	    }catch (NoSuchElementException e) {
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de la factura no existe");
	    }
	}

	@Override
	public ApiResponse create() {
		try {
			/*
			* Proyecto: Requerimiento 2 - Finalizar compra
			*/
			Integer userId = jwtDecoder.getUserId();
			List<DtoCartItemOut> cartItems = repoCartItem.getCartItems(userId);

			if (cartItems == null || cartItems.isEmpty()) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "El carrito está vacío");
			}
			Double total = 0.0;
			try {
				for (DtoCartItemOut item : cartItems) {
					String gtin = item.getGtin();
					Integer quantity = item.getQuantity();
					Product product = repoProduct.findByGtin(gtin);

					if (product == null) {
						throw new ApiException(HttpStatus.NOT_FOUND, "El producto con GTIN " + gtin + " no fue encontrado");
					}
					if (product.getStatus() == 0) {
						throw new ApiException(HttpStatus.NOT_FOUND, "El producto con GTIN " + gtin + " no está disponible");
					}
					int productStock = product.getStock();
					if (productStock < quantity) {
						throw new ApiException(HttpStatus.BAD_REQUEST, "El stock del producto con GTIN " + gtin + " no es suficiente.");
					}
					Double price = (double) product.getPrice();
					Double itemTotal = calcTotal(price, quantity);
					total += itemTotal;
				}
			} catch (DataAccessException e) {
				throw new DBAccessException("Error al procesar items del carrito", e);
			}
			Double taxes = calcTaxes(total, 0.16);
			Double subTotal = calcSubTotal(total, taxes);
			Invoice invoice = new Invoice();
			invoice.setUser_id(userId);
			invoice.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			invoice.setTotal(total);
			invoice.setTaxes(taxes);
			invoice.setSubtotal(subTotal);
			repo.save(invoice); 
			try {
				for (DtoCartItemOut cartItem : cartItems) {
					String gtin = cartItem.getGtin();
					Integer quantity = cartItem.getQuantity();
					Product product = repoProduct.findByGtin(gtin);
					int productStock = product.getStock();
					Double price = (double) product.getPrice();
					Double itemTotal = calcTotal(price, quantity);
					Double itemTaxes = calcTaxes(itemTotal, 0.16);
					Double itemSubTotal = calcSubTotal(itemTotal, itemTaxes);
					InvoiceItem invItem = new InvoiceItem();
					Integer invoiceId = invoice.getInvoice_id();
					invItem.setInvoice_id(invoiceId); 
					invItem.setGtin(gtin);
					invItem.setQuantity(quantity);
					invItem.setUnit_price(price);
					invItem.setSubtotal(itemSubTotal);
					invItem.setTaxes(itemTaxes);
					invItem.setTotal(itemTotal);
					invItem.setStatus(1);
					repoInvoiceItem.save(invItem);
					product.setStock(productStock - quantity);
					repoProduct.save(product);
				}
			} catch (DataAccessException e) {
				throw new DBAccessException("Error al guardar los invoice items o actualizar stock", e);
			}
			/* Vaciar el carrito */
			repoCartItem.deleteByUserId(userId);
			return new ApiResponse("La factura ha sido registrada");
		} catch (DataAccessException e) {
			throw new DBAccessException();
		}
	}
	
	private Double calcTotal(Double price, int quantity) {
		return price * quantity;
	}

	private Double calcTaxes(Double total, Double taxes) {
		return total * taxes;
	}

	private Double calcSubTotal(Double total, Double taxes) {
		return total - taxes;
	}

	@Override
	public ApiResponse checkout(Integer addressId, String couponCode) {
		try {
			Integer userId = jwtDecoder.getUserId();
			List<DtoCartItemOut> cartItems = repoCartItem.getCartItems(userId);
			if(cartItems == null) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "El carrito está vacío");
            }
			Double total = 0.0;
			List<InvoiceItem> invoiceItems = new ArrayList<>();
			for (DtoCartItemOut item : cartItems) {
				String gtin = item.getGtin();
				Integer quantity = item.getQuantity();
				Product product = repoProduct.findByGtin(gtin);
				if (product == null) {
					throw new ApiException(HttpStatus.NOT_FOUND, "El producto con GTIN " + gtin + " no fue encontrado");
				}
				if (product.getStatus() == 0) {
					throw new ApiException(HttpStatus.NOT_FOUND, "El producto con GTIN " + gtin + " no está disponible");
				}
				int productStock = product.getStock();
				if (productStock < quantity) {
					throw new ApiException(HttpStatus.BAD_REQUEST, "El stock del producto con GTIN " + gtin + " no es suficiente.");
				}
				Double price = (double) product.getPrice();
				/* Guardamos cada elemento (invoice item) en una lista temporal para no volver a gaurdar datos como gtin o product en otra iteración */
				InvoiceItem invItem = new InvoiceItem();
				Double itemTotal = calcTotal(price, quantity);
				Double itemTaxes = calcTaxes(itemTotal, 0.16);
				Double itemSubTotal = calcSubTotal(itemTotal, itemTaxes);
				invItem.setGtin(gtin);
				invItem.setQuantity(quantity);
				invItem.setUnit_price(price);
				invItem.setSubtotal(itemSubTotal);
				invItem.setTaxes(itemTaxes);
				invItem.setTotal(itemTotal);
				invoiceItems.add(invItem);
				/* Actualizamos la cantidad final de la factura (el conjunto de todos los productos/items asociados) */
				total += itemTotal;
				product.setStock(productStock - quantity);
				repoProduct.save(product);
			}
			/* Verificación y aplicación de descuentos con cupones */
			Double discountApplied = 0.0;
            Double totalWithDiscount = svcCoupon.validateAndApplyCoupon(couponCode, total);
            if (totalWithDiscount < total) {
                discountApplied = total - totalWithDiscount;
                svcCoupon.incrementCouponUse(couponCode);
            }
			/* Calculo de taxes y subtotal */
			Double taxes = calcTaxes(totalWithDiscount, 0.16);
			Double subTotal = calcSubTotal(totalWithDiscount, taxes);
			/* Creamos la entidad para la factura y agregamos sus elementos. */
			Invoice invoice = new Invoice();
			/* Dirección de envío */
			Address address = repoAddress.findByUserIdAndAddressId(userId, addressId);
			if (address == null) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "La dirección especificada no existe o no pertenece al usuario");
			}
			invoice.setUser_id(userId);
			invoice.setAddress_id(addressId);
			invoice.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			invoice.setTotal(total);
			invoice.setTaxes(taxes);
			invoice.setSubtotal(subTotal);
			invoice.setCouponCode(couponCode);
            invoice.setDiscountApplied(discountApplied);
			repo.save(invoice);
			for (InvoiceItem item : invoiceItems) {
				item.setInvoice_id(invoice.getInvoice_id());				
				repoInvoiceItem.save(item);
			}
			repoCartItem.deleteByUserId(userId);
			return new ApiResponse("La factura ha sido registrada"); 
		} catch (DataAccessException e) {
	        throw new DBAccessException();
	    }
	}
}