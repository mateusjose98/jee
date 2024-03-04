package com.tutorial.service;

import com.tutorial.model.Category;
import com.tutorial.model.Product;
import com.tutorial.repository.CategoriaRepository;
import com.tutorial.repository.ProdutoRepository;
import com.tutorial.repository.Repository;
import com.tutorial.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private Repository<Product> productRepository;
    private Repository<Category> categoryRepository;

    public CatalogServiceImpl() {
        this.productRepository = new ProdutoRepository();
        this.categoryRepository = new CategoriaRepository();
    }

    @Override
    public List<Product> list() throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            productRepository.setCoon(conn);
            return productRepository.findAll();
        }

    }

    @Override
    public Product byIdProd(Integer id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            productRepository.setCoon(conn);
            return productRepository.find(id).orElse(null);
        }

    }

    @Override
    public Product save(Product product) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            productRepository.setCoon(conn);
            if(conn.getAutoCommit()) conn.setAutoCommit(false);
            Product newProduct = new Product();
            try {
                newProduct = productRepository.save(product);
                conn.commit();
            }catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

            return newProduct;
        }

    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            productRepository.setCoon(conn);
            if(conn.getAutoCommit()) conn.setAutoCommit(false);

            try {
                productRepository.delete(id);
                conn.commit();
            }catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Category> listCategories() throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            categoryRepository.setCoon(conn);
            return categoryRepository.findAll();
        }

    }

    @Override
    public Category byId(Integer id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            categoryRepository.setCoon(conn);
            return categoryRepository.find(id).orElse(null);
        }
    }

    @Override
    public Category saveCategory(Category category) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            categoryRepository.setCoon(conn);
            if(conn.getAutoCommit()) conn.setAutoCommit(false);
            Category newCategory = new Category();
            try {
                newCategory = categoryRepository.save(category);
                conn.commit();
            }catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            return newCategory;
        }

    }

    @Override
    public void deleteCategory(Integer id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            categoryRepository.setCoon(conn);
            if(conn.getAutoCommit()) conn.setAutoCommit(false);

            try {
                categoryRepository.delete(id);
                conn.commit();
            }catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void saveProductWithCategory(Product product, Category category) throws SQLException {
        try (Connection conn = DBUtil.getConnection()){
            productRepository.setCoon(conn);
            categoryRepository.setCoon(conn);

            if(conn.getAutoCommit()) conn.setAutoCommit(false);
            Category newCategory;
            Product product1 = new Product();
            try {
                newCategory = categoryRepository.save(category);
                product.setCategoria(newCategory);
                product1 = productRepository.save(product);
                conn.commit();
            }catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }
}
