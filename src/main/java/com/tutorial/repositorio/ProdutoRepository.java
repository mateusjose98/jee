package com.tutorial.repositorio;

import com.tutorial.model.Categoria;
import com.tutorial.model.Produto;
import com.tutorial.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository implements Repository<Produto> {
    private Connection getConnection() throws SQLException {
            return Conexao.getConnection();

    }
    @Override
    public Optional<Produto> find(Integer id) {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement("select p.*, c.nome as categoria from produtos p left join categorias c on p.categoria_id = c.id WHERE p.id = ?")) {
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
            ResultSet rs = statement.executeQuery("select p.*, c.nome as categoria from produtos p left join categorias c on p.categoria_id = c.id")){
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
        produto.setCategoria(new Categoria(rs.getInt("categoria_id"), rs.getString("categoria")));
        return produto;
    }

    @Override
    public void save(Produto atualizacao) {
        String sql;
        boolean criacao = atualizacao.getId() == null;
        if(criacao) {
            sql = "INSERT INTO produtos(nome, preco, categoria_id, data_registro) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE produtos SET nome = ?, preco = ?, categoria_id = ?  WHERE id = ?";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1, atualizacao.getNome());
            stmt.setDouble(2, atualizacao.getPreco());
            stmt.setInt(3, atualizacao.getCategoria().getId());
            if(criacao) {
                stmt.setDate(4, new Date(new java.util.Date().getTime()));
            } else {
                stmt.setInt(4, atualizacao.getId());
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
