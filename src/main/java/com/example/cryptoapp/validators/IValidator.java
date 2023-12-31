package com.example.cryptoapp.validators;

import com.example.cryptoapp.models.BaseModel;

public interface IValidator<T extends BaseModel> {
    void validateEntity(T entity);
}
