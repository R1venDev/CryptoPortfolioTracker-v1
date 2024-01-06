package com.example.cryptoapp.viewmodels;

import com.example.cryptoapp.models.Portfolio;

public class PortfolioViewModel extends Portfolio{
    private double pnl;

    public PortfolioViewModel(Portfolio portfolio, double pnl) {
        super(portfolio.id, portfolio.getUserId(), portfolio.getName());
        this.pnl = pnl;
    }

    public double getPnl() {
        return pnl;
    }
}
