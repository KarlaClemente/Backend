package com.product;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/** 
 * Clase CategoryService, actúa como un servicio se Spring encargado de gestionar operaciones relacionadas con las 
 * categorías (por ahora solo agrega objetos a una lista).
 */
@Service
public class CategoryService {
    /* Lista de categorías. */
    private final List<Category> categories = new ArrayList<>();

    /* Constructor de la clase que carga la lista de categorías. */
    public CategoryService() {
        addCategories();
    }

    /* Método privado auxiliar encargado de crear categorías y agregarlas a una lista. */
    private void addCategories() {
        categories.add(new Category(1, "Aretes", "arts", 1));
        categories.add(new Category(2, "Collares", "cllrs", 1));
        categories.add(new Category(3, "Dijes", "djs", 1));
        categories.add(new Category(4, "Pulseras", "plsrs", 1));
        categories.add(new Category(5, "Anillos", "anlls", 1));
        categories.add(new Category(6, "Relojes", "rljs", 1));
    }

    /**
     * Método encargado de regresar una lista con todas las categorías.
     * @return una lista de categorías.
     */
    public List<Category> getAllCategories() {
        return categories;
    }
}
