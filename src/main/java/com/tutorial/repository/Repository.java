package com.tutorial.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void setCoon(Connection coon);
    Optional<T> find(Integer id) throws SQLException;
    List<T> findAll() throws SQLException;
    T save(T atualizado) throws SQLException;
    void delete(Integer id) throws SQLException;
}
