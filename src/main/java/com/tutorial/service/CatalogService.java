package com.tutorial.service;

import com.tutorial.model.Category;
import com.tutorial.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface CatalogService {
    List<Product> list() throws SQLException;
    Product byIdProd(Integer id) throws SQLException;
    Product save(Product product) throws SQLException;
    void delete(Integer id) throws SQLException;
    List<Category> listCategories() throws SQLException;
    Category byId(Integer id) throws SQLException;
    Category saveCategory(Category category) throws SQLException;
    void deleteCategory(Integer id) throws SQLException;
    void saveProductWithCategory(Product product, Category category) throws SQLException;


}
