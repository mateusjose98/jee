package com.tutorial.model;

import java.time.LocalDateTime;

public class Product {
    private Integer id;
    private String nome;
    private Double preco;
    private LocalDateTime dateTime;
    private Category categoria;
    private String sku;

    public Product(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return id + "\t|\t" + nome + "\t|\t" + preco + "\t|\t" + dateTime + "\t|\t"+ categoria.getNome() + "\t|\t" + sku;
    }
}
