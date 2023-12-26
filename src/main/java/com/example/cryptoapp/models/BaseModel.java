package com.example.cryptoapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue
    @Column(name="id")
    public Long id;

    public Long getId() {
        return id;
    }
}
