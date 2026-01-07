package com.product;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase CtrlProduct encargada de definir los endpoints relacionados con la clase Catgeory.
 */
@RestController
@RequestMapping("/category") 
public class CtrlProduct {
    /* Un objeto de tipo CategoryService para poder acceder a las operaciones de gestión de categorías. */
    private final  CategoryService categoryService = new CategoryService();

    /**
     * Endpoint (GET) para obtener la lista de categorías.
     * @return una lista de categorías. 
     */
    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }
}
