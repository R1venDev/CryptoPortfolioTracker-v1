package com.example.cryptoapp.models;


import jakarta.persistence.*;

@Entity
@Table(name = "Portfolio", schema = "cryptoappschema")
public class Portfolio extends BaseModel {
    @Column(name="userId")
    private Long userId;

    @Column(name="name")
    private String name;

    @Column(name="pnl")
    private double pnl;

    public Portfolio(Long id, Long userId, String portfolioName, double pnl) {
        this.id = id;
        this.userId = userId;
        this.name = portfolioName;
        this.pnl = pnl;
    }

    public Portfolio() {

    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnlChange) {
        this.pnl = pnlChange;
    }
}
