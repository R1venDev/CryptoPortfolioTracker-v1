package com.example.cryptoapp.validators;

import com.example.cryptoapp.exceptions.ValidationException;
import com.example.cryptoapp.models.Trade;

public class TradeValidator implements IValidator<Trade> {
    public void validateEntity(Trade trade) throws ValidationException {
        if (trade.getStartDate() == null || trade.getEndDate() == null || trade.getStartDate().after(trade.getEndDate())) {
            throw new ValidationException("Invalid trade dates. End date must be after start date.");
        }

        if (trade.getTradeStatus() == null) {
            throw new ValidationException("Trade status cannot be null.");
        }

        if (trade.getTradeType() == null) {
            throw new ValidationException("Trade type cannot be null.");
        }

        if (trade.getAssetQuantity() <= 0) {
            throw new ValidationException("Asset quantity must be a positive number.");
        }

        if (trade.getAssetPrice() <= 0) {
            throw new ValidationException("Asset price must be a positive number.");
        }
    }
}
