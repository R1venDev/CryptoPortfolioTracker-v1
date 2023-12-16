package com.example.cryptoapp.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Portfolio {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userId;
    private String name;
    private double pnl;


    public Portfolio(Long id, int userId, String portfolioName, double pnl) {
        this.id = id;
        this.userId = userId;
        this.name = portfolioName;
        this.pnl = pnl;
    }

    public Long getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getPortfolioName() {
        return name;
    }

    public double getPnl() {
        return pnl;
    }

    public void updatePnl(double pnlChange) {
        this.pnl = pnlChange;
    }
}
