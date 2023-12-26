package com.example.cryptoapp.repositories;

import com.example.cryptoapp.models.BaseModel;

import java.util.List;

public interface IRepository<T extends BaseModel> {

    void save(T entity);

    T findById(Long id);

    List<T> findAll();

    void update(T entity);

    void delete(Long id);
}