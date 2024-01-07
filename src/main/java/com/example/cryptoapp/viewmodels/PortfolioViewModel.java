package com.example.cryptoapp.viewmodels;

import com.example.cryptoapp.models.Portfolio;

public class PortfolioViewModel extends Portfolio{
    public double pnl;

    public PortfolioViewModel(Portfolio portfolio, double pnl) {
        super(portfolio.id, portfolio.getUserId(), portfolio.getName());
        this.pnl = pnl;
    }

    public double getPnl () {
        return this.pnl;
    }
}
