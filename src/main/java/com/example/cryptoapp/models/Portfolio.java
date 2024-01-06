package com.example.cryptoapp.models;


import jakarta.persistence.*;

@Entity
@Table(name = "Portfolio", schema = "cryptoappschema")
public class Portfolio extends BaseModel {
    @Column(name="userId")
    private Long userId;

    @Column(name="name")
    private String name;

    public Portfolio(Long id, Long userId, String portfolioName) {
        this.id = id;
        this.userId = userId;
        this.name = portfolioName;
    }

    public Portfolio() {

    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

}
