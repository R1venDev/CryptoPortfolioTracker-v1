package com.example.cryptoapp.services;

import com.example.cryptoapp.models.BaseModel;

import java.util.List;

public interface IEntityService<T extends BaseModel> {

    void add(T entity);

    T findById(Long id);

    List<T> findAll();

    void update(T entity);

    void deleteById(Long id);
}
