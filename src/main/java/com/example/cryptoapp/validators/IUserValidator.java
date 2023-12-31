package com.example.cryptoapp.validators;

import com.example.cryptoapp.models.User;

import java.util.List;

public interface IUserValidator extends IValidator<User>{
    public void doExtraValidationChecks(User user, List<User> existingUsers);
}
