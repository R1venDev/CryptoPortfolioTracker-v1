package com.example.cryptoapp.repositories;

import java.util.List;

public interface IRepository<T> {

    void save(T product);

    T findById(Long id);

    List<T> findAll();

    void update(T product);

    void delete(Long id);
}