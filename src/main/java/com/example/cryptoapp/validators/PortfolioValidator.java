package com.example.cryptoapp.validators;

import com.example.cryptoapp.exceptions.ValidationException;
import com.example.cryptoapp.models.Portfolio;

public class PortfolioValidator implements IValidator<Portfolio> {
    public void validateEntity(Portfolio portfolio) throws ValidationException {
        if (portfolio.getUserId() <= 0) {
            throw new ValidationException("User ID must be a positive integer.");
        }

        if (portfolio.getPortfolioName() == null || portfolio.getPortfolioName().isEmpty()) {
            throw new ValidationException("Portfolio name cannot be empty.");
        }
    }
}
