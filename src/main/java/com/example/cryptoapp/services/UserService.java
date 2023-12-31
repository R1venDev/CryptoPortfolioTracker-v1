package com.example.cryptoapp.services;

import com.example.cryptoapp.exceptions.ValidationException;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.repositories.IRepository;
import com.example.cryptoapp.validators.IUserValidator;

public class UserService extends GenericEntityService<User> {
    public IUserValidator userValidator;

    public UserService(IRepository<User> repository, IUserValidator validator) {
        super(repository, validator);

        this.userValidator = validator;
    }

    @Override
    public void add(User entity) throws ValidationException {
        validator.validateEntity(entity);
        userValidator.doExtraValidationChecks(entity, repository.findAll());

        repository.save(entity);
    }

    @Override
    public void update(User entity) throws ValidationException {
        validator.validateEntity(entity);
        userValidator.doExtraValidationChecks(entity, repository.findAll());

        repository.update(entity);
    }
}
