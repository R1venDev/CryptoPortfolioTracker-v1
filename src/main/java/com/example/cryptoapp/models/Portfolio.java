package com.example.cryptoapp.models;


import jakarta.persistence.*;

@Entity
@Table(name = "Portfolio", schema = "cryptoappschema")
public class Portfolio extends BaseModel {
    private int userId;
    private String name;
    private double pnl;

    public Portfolio(Long id, int userId, String portfolioName, double pnl) {
        this.id = id;
        this.userId = userId;
        this.name = portfolioName;
        this.pnl = pnl;
    }

    public Portfolio() {

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
