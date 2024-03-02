package com.tutorial;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> find(Integer id);
    List<T> findAll();
    void save(T atualizado);
    void delete(Integer id);
}
