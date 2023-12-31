package com.example.cryptoapp.services;

import com.example.cryptoapp.exceptions.ValidationException;
import com.example.cryptoapp.models.BaseModel;
import com.example.cryptoapp.repositories.IRepository;
import com.example.cryptoapp.validators.IValidator;

import java.util.List;

public class GenericEntityService<T extends BaseModel> implements IEntityService<T>  {
    protected IRepository<T> repository;
    protected IValidator<T> validator;

    public GenericEntityService(IRepository<T> repository, IValidator<T> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void add(T entity) throws ValidationException {
        validator.validateEntity(entity);
        repository.save(entity);
    }

    public T findById(Long id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void update(T entity) throws ValidationException {
        validator.validateEntity(entity);
        repository.update(entity);
    }

    public void deleteById(Long id) {
        repository.delete(id);
    }
}
