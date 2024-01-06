package com.example.cryptoapp.services;

import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.repositories.IRepository;
import com.example.cryptoapp.validators.IValidator;

public class PortfolioService extends GenericEntityService<Portfolio> implements IPortfolioService {

    private IRepository <Trade> tradeRepository;

    public PortfolioService(IRepository <Trade> tradeRepository, IRepository <Portfolio> repository, IValidator<Portfolio> validator) {
        super(repository, validator);
        this.tradeRepository = tradeRepository;
    }

    public double getPNL (Long portfolioId, double assetPriceNow) {
        return tradeRepository.findAll().stream()
                        .filter(x -> x.getPortfolioId() == portfolioId)
                .mapToDouble(x -> x.getPNL(assetPriceNow)).sum();

    }
}
