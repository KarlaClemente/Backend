package com.product;

/* Clase Category, encargada de definir un objeto de tipo categoría. Una categoría cuenta con ID, nombre, tag y estatus */
public class Category {
    /* Un entero que representa el ID de una categoría. */
    private int categoryId;
    /* Una cadena con el nombre de una categoría. */
    private String category;
    /* Una cadena con el tag de una categoría. */
    private String tag;
    /* Un entero con el estatus (0 o 1) de una categoría. */
    private int status;

    /**
     * Constructor de un objeto de tipo categoría.
     * @param categoryId el número de ID de una categoría.
     * @param category el nombre de una categoría en formato de cadena.
     * @param tag el tag de una categoría en formato de cadena.
     * @param status un número, 0 o 1, depenediendo del estatus de la categoría.
     */
    public Category(int categoryId, String category, String tag, int status) {
        this.categoryId = categoryId;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }    

    /**
     * Regresa el ID de una categoría.
     * @return un entero con el ID de una categoría.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Define el ID de una categoría.
     * @param categoryId el nuevo ID de una categoría.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Regresa el nombre de una categoría.
     * @return una cadena con el nombre de una categoría.
     */    
    public String getCategory() {
        return category;
    }

    /**
     * Define el nombre de una categoría.
     * @param category el nuevo nombre de una categoría.
     */
    public void setCategory(String category) {
            this.category = category;
    }

    /**
     * Regresa el tag de una categoría.
     * @return una cadena con el tag de una categoría.
     */
    public String getTag() {
        return tag;
    }
        
    /**
     * Define el tag de una categoría.
     * @param tag el nuevo tag de una categoría.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Regresa el estatus de una categoría.
     * @return 0 o 1 dependiendo del estatus de la categoría.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Define el estatus de una categoría.
     * @param status el nuevo estatus de una categoría.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
