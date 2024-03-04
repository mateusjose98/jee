package com.tutorial.repository;

import com.tutorial.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaRepository implements Repository<Category> {
    private Connection coon;

    public CategoriaRepository(Connection coon) {
        this.coon = coon;
    }

    public CategoriaRepository() {
    }

    @Override
    public Optional<Category> find(Integer id) throws SQLException {
        try (PreparedStatement stmt = coon
                .prepareStatement("select * from categorias WHERE p.id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(mapResultSetToCategoria(rs));
            }
            rs.close();
        }
        return Optional.empty();
    }

    private static Category mapResultSetToCategoria(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("id"), rs.getString("nome"));
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> result = new ArrayList<>();
        try(Statement statement = coon.createStatement();
            ResultSet rs = statement.executeQuery("select * from categorias")){
            while(rs.next()) {
                Category cat = mapResultSetToCategoria(rs);
                result.add(cat);
            }
        }
        return result;
    }

    @Override
    public Category save(Category atualizado) throws SQLException {
        String sql;
        boolean criacao = atualizado.getId() == null;
        if(criacao) {
            sql = "INSERT INTO categorias(nome) VALUES (?)";
        } else {
            sql = "UPDATE categorias SET nome = ?  WHERE id = ?";
        }

        try (PreparedStatement stmt = coon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, atualizado.getNome());
            if(!criacao) {
                stmt.setInt(2, atualizado.getId());
            }
            stmt.executeUpdate();

            if(atualizado.getId() == null) {
                ResultSet idRs = stmt.getGeneratedKeys();
                idRs.next();
                atualizado.setId(idRs.getInt(1));
            }

            return atualizado;

        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try(PreparedStatement statement = coon.prepareStatement("DELETE FROM categorias WHERE id = ?")){
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public void setCoon(Connection coon) {
        this.coon = coon;
    }
}
