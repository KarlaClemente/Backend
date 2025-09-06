package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

    @GetMapping
    public List<Category> getCategory() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category(1, "lentes", "Lts", 1));
        categories.add(new Category(2, "ropa", "Rp", 1));
        categories.add(new Category(3, "carteras", "Crtrs", 1));
        categories.add(new Category(4, "zapatos", "Zpts", 1));
        return categories;
    }
}
