package com.tutorial;

import com.tutorial.model.Produto;
import com.tutorial.util.Conexao;

import java.sql.*;
import java.util.Arrays;
import java.util.Optional;

public class ExemploJDBC {

    public static void main(String[] args) {

        try(Connection connection = Conexao.getConnection()) // abre e finaliza a conexão singleton
        {
            Repository<Produto> repository = new ProdutoRepository();
            System.out.println("============================ LISTAR ==========================");
            repository.findAll().forEach(System.out::println);
            System.out.println("============================ PRODUTO ID = 1 ==========================");
            Optional<Produto> buscadoOpt = repository.find(1);
            if(buscadoOpt.isPresent()) {
                System.out.println(buscadoOpt);
                Produto produtoBuscado = buscadoOpt.get();
                produtoBuscado.setPreco(340.0);
                repository.save(produtoBuscado);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
