package com.stock.service.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CoinBuyHelper {
    public static BigDecimal convertCoinByAmount(BigDecimal amount, BigDecimal coinPrise){
        return amount.divide(coinPrise, 10, RoundingMode.HALF_UP);
    }

    public static BigDecimal convertCoinAmountToUSD(){
        return null;
    }

    public static BigDecimal convertToUSD(String amount){
        return new BigDecimal(amount);
    }
    public static BigDecimal convertToUSD(Long amount){
        return BigDecimal.valueOf(amount);
    }
}
