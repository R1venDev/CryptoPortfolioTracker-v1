package com.example.cryptoapp.validators;

import com.example.cryptoapp.exceptions.ValidationException;
import com.example.cryptoapp.models.User;

import java.util.List;

public class UserValidator implements IUserValidator{
    public void validateEntity(User user) throws ValidationException {
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new ValidationException("User first name cannot be empty.");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ValidationException("User lastname cannot be empty.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("User email cannot be empty.");
        }

        // other User validation logic
        // that doesn't require repository calls
    }

    public void doExtraValidationChecks(User user, List<User> existingUsers) {
        boolean isEmailTaken = existingUsers.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

        if (isEmailTaken) {
            throw new ValidationException("User with this email already exists.");
        }

        // other User validation logic
        // that REQUIRES repository calls
        // for example repo.findAll();
    }
}
