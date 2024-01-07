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
    @Column(name="tradeResult")
    private TradeResult tradeResult;

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

    public Trade(Long id, Long portfolioId, Date startDate, Date endDate, TradeStatus tradeStatus,
                 TradeResult tradeResult, TradeType tradeType, double assetQuantity,
                 double assetPrice, double leverage) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tradeStatus = tradeStatus;
        this.tradeResult = tradeResult;
        this.tradeType = tradeType;
        this.assetQuantity = assetQuantity;
        this.assetPrice = assetPrice;
        if (tradeType == TradeType.LONG || tradeType == TradeType.SHORT) {
            this.leverage = leverage;
        }
        else { this.leverage = 1.0; }
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

    public TradeResult getTradeResult() {
        return tradeResult;
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

    public double countTradeAmount() {
        return assetPrice * assetQuantity * leverage;
    }

    public double getLeverage() {
        return leverage;
    }

    public double countPNL(double assetPriceNow) {
        return (assetPriceNow-assetPrice)*leverage;
    }

}



