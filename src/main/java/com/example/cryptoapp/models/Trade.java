package com.example.cryptoapp.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Trade", schema = "cryptoappschema")
public class Trade extends BaseModel {
    @Column(name="portfolioId")
    private Long portfolioId;

    @Column(name="startDate")
    private Date startDate;

    @Column(name="endDate")
    private Date endDate;
    @Enumerated(EnumType.STRING)
    @Column(name="tradeStatus")
    private TradeStatus tradeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="tradeState")
    private TradeResult tradeState;

    @Enumerated(EnumType.STRING)
    @Column(name="tradeType")
    private TradeType tradeType;

    @Column(name="assetQuantity")
    private double assetQuantity;

    @Column(name="assetPrice")
    private double assetPrice;

    @Column(name="assetAmount")
    private double tradeAmount;

    @Column(name="leverage")
    private double leverage;

    public Trade(Long id, Date startDate, Date endDate, TradeStatus tradeStatus,
                 TradeResult tradeState, TradeType tradeType, double assetQuantity,
                 double assetPrice, double leverage) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tradeStatus = tradeStatus;
        this.tradeState = tradeState;
        this.tradeType = tradeType;
        this.assetQuantity = assetQuantity;
        this.assetPrice = assetPrice;
        if (tradeType == TradeType.LONG || tradeType == TradeType.SHORT) {
            this.leverage = leverage;
        }
        else { this.leverage = 1.0; }
        this.tradeAmount = assetPrice * assetQuantity * leverage;
    }

    public Trade() {

    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public TradeResult getTradeState() {
        return tradeState;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public double getAssetQuantity() {
        return assetQuantity;
    }

    public double getAssetPrice() {
        return assetPrice;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public double getLeverage() {
        return leverage;
    }
}


