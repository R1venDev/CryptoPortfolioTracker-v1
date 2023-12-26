package com.example.cryptoapp.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Trade", schema = "cryptoappschema")
public class Trade extends BaseModel {
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;
    @Enumerated(EnumType.STRING)
    private TradeResult tradeState;
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    private double assetQuantity;
    private double assetPrice;
    private double tradeAmount;
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
        if (tradeType == TradeType.LONG || tradeType == TradeType.SHORT)
        {
            this.leverage = leverage;}
        else this.leverage=1.0;
        this.tradeAmount = assetPrice * assetQuantity * leverage;
    }

    public Trade() {

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


