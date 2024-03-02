package com.tutorial;

import com.tutorial.model.Produto;
import com.tutorial.repositorio.ProdutoRepository;
import com.tutorial.repositorio.Repository;
import com.tutorial.util.Conexao;

import java.sql.*;
import java.util.Optional;

public class ExemploJDBC {

    public static void main(String[] args) {

            Repository<Produto> repository = new ProdutoRepository();

            repository.findAll().forEach(System.out::println);
            Optional<Produto> buscadoOpt = repository.find(1);

            if(buscadoOpt.isPresent()) {
                System.out.println(buscadoOpt.get());
                Produto produtoBuscado = buscadoOpt.get();
                produtoBuscado.setPreco(340.0);
                repository.save(produtoBuscado);
            }

    }



}
