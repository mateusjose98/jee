package com.tutorial;


import com.tutorial.model.Category;
import com.tutorial.model.Product;

import com.tutorial.service.CatalogService;
import com.tutorial.service.CatalogServiceImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Category category = new Category();
        category.setNome("Roupas");
        CatalogService service = new CatalogServiceImpl();
        Product product = new Product();
        product.setNome("Camiseta");
        product.setPreco(50.0);
        product.setSku("abc");
        product.setCategoria(category);

        service.saveProductWithCategory(product, category);

        service.list();
        service.listCategories();


    }

}
