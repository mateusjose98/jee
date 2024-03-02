package com.tutorial;

import com.tutorial.model.Produto;
import com.tutorial.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository implements Repository<Produto> {
    private Connection getConnection() throws SQLException {
            return Conexao.getConnection();

    }
    @Override
    public Optional<Produto> find(Integer id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM produtos WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(mapResultSetToProduto(rs));
            }
            rs.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Produto> findAll() {
        List<Produto> result = new ArrayList<>();
        try(Statement statement = this.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM produtos")){
            while(rs.next()) {
                Produto produto = mapResultSetToProduto(rs);
                result.add(produto);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static Produto mapResultSetToProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setDateTime(rs.getDate("data_registro").toLocalDate().atStartOfDay());
        return produto;
    }

    @Override
    public void save(Produto atualizacao) {
        String sql;
        boolean criacao = atualizacao.getId() == null;
        if(criacao) {
            sql = "INSERT INTO produtos(nome, preco, data_registro) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE produtos SET nome = ?, preco = ?  WHERE id = ?";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1, atualizacao.getNome());
            stmt.setDouble(2, atualizacao.getPreco());

            if(criacao) {
                stmt.setDate(3, new Date(new java.util.Date().getTime()));
            } else {
                stmt.setInt(3, atualizacao.getId());
            }

            stmt.executeUpdate();
        }
        catch (Exception e){}
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM produtos WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {}

    }
}
