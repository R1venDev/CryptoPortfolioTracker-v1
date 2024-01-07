package com.example.cryptoapp.viewmodels;

import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.Trade;

public class TradeViewModel extends Trade {

    public double pnl;
    public double tradeAmount;

    public TradeViewModel(Trade trade, double assetsPriceNow) {
        super(trade.id, trade.getPortfolioId(), trade.getStartDate(), trade.getEndDate(), trade.getTradeStatus(),
                trade.getTradeResult(), trade.getTradeType(), trade.getAssetQuantity(), trade.getAssetPrice(),  trade.getLeverage());
        this.pnl = countPNL(assetsPriceNow);
        this.tradeAmount = countTradeAmount();
    }

    public double getPnl() {
        return this.pnl;
    }

    public double getTradeAmount() {
        return this.tradeAmount;
    }
}