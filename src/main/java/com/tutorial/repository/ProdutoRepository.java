package com.tutorial.repository;

import com.tutorial.model.Category;
import com.tutorial.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository implements Repository<Product> {
    private Connection coon;

    public ProdutoRepository(Connection coon) {
        this.coon = coon;
    }

    public ProdutoRepository() {
    }

    @Override
    public Optional<Product> find(Integer id) throws SQLException {
        try (PreparedStatement stmt = coon
                .prepareStatement("select p.*, c.nome as categoria from produtos p left join categorias c on p.categoria_id = c.id WHERE p.id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(mapResultSetToProduto(rs));
            }
            rs.close();
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> result = new ArrayList<>();
        try(Statement statement = coon.createStatement();
            ResultSet rs = statement.executeQuery("select p.*, c.nome as categoria from produtos p left join categorias c on p.categoria_id = c.id")){
            while(rs.next()) {
                Product produto = mapResultSetToProduto(rs);
                result.add(produto);
            }


        }
        return result;
    }

    private static Product mapResultSetToProduto(ResultSet rs) throws SQLException {
        Product produto = new Product();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setDateTime(rs.getDate("data_registro").toLocalDate().atStartOfDay());
        produto.setSku(rs.getString("sku"));
        produto.setCategoria(new Category(rs.getInt("categoria_id"), rs.getString("categoria")));
        return produto;
    }

    @Override
    public Product save(Product atualizacao) throws SQLException {
        String sql;
        boolean criacao = atualizacao.getId() == null;
        if(criacao) {
            sql = "INSERT INTO produtos(nome, preco, categoria_id, sku, data_registro) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE produtos SET nome = ?, preco = ?, categoria_id = ?, sku = ?  WHERE id = ?";
        }

        try (PreparedStatement stmt = coon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, atualizacao.getNome());
            stmt.setDouble(2, atualizacao.getPreco());
            stmt.setInt(3, atualizacao.getCategoria().getId());
            stmt.setString(4, atualizacao.getSku());
            if(criacao) {
                stmt.setDate(5, new Date(new java.util.Date().getTime()));
            } else {
                stmt.setInt(5, atualizacao.getId());
            }
            stmt.executeUpdate();
            if(atualizacao.getId() == null) {
                ResultSet idRs = stmt.getGeneratedKeys();
                idRs.next();
                atualizacao.setId(idRs.getInt(1));
            }

            return atualizacao;

        }

    }

    @Override
    public void delete(Integer id) throws SQLException {
        try(PreparedStatement stmt = coon.prepareStatement("DELETE FROM produtos WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

    }

    public void setCoon(Connection coon) {
        this.coon = coon;
    }
}
