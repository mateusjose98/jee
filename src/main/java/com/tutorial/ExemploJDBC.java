package com.tutorial;

import com.tutorial.model.Categoria;
import com.tutorial.model.Produto;
import com.tutorial.repositorio.ProdutoRepository;
import com.tutorial.repositorio.Repository;
import com.tutorial.util.Conexao;

import java.sql.*;
import java.util.Optional;

public class ExemploJDBC {

    public static void main(String[] args) {

            Repository<Produto> repository = new ProdutoRepository();

            Produto p = new Produto();
            p.setNome("aaaaaaa");
            p.setPreco(10.20);
            p.setCategoria(new Categoria(1, "aaa"));
            repository.save(p);


            repository.findAll().forEach(System.out::println);

//            Optional<Produto> buscadoOpt = repository.find(1);
//
//            if(buscadoOpt.isPresent()) {
//                System.out.println(buscadoOpt.get());
//                Produto produtoBuscado = buscadoOpt.get();
//                produtoBuscado.setPreco(340.0);
//                repository.save(produtoBuscado);
//            }

    }



}
