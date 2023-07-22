package com.stock.service.api;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.model.account.AccountType;
import com.stock.model.stock.analytic.RiskType;
import com.stock.service.analytic.PortfolioRiskAnalyzer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SAndP500Test {
//    @Autowired
//    private AccountService accountService;
    @Autowired
    private PortfolioRiskAnalyzer riskAnalyzer;

//    @Autowired
//private StockCalculator stockCalculator;
    @Test
    void test() {
        AccountDto account = new AccountDto();

        List<AccountStockDto> accountStockDtos = new ArrayList<>();


//
//        AccountStockDto T = new AccountStockDto();
//        T.setSymbol("T");
//        accountStockDtos.add(T);

//        AccountStockDto NVDA = new AccountStockDto();
//        NVDA.setSymbol("NVDA");
//        accountStockDtos.add(NVDA);
//
        AccountStockDto TSLA = new AccountStockDto();
        TSLA.setSymbol("TSLA");
        accountStockDtos.add(TSLA);

//        AccountStockDto BABA = new AccountStockDto();
//        BABA.setSymbol("BABA");
//        accountStockDtos.add(BABA);

        AccountStockDto KO = new AccountStockDto();
        KO.setSymbol("KO");
        accountStockDtos.add(KO);
//
//        AccountStockDto AAPL = new AccountStockDto();
//        AAPL.setSymbol("AAPL");
//        accountStockDtos.add(AAPL);
//
        account.setStocks(accountStockDtos);
        account.setAccountType(AccountType.StockWallet.name());
        RiskType riskStockPortfolio = riskAnalyzer.getRiskStockPortfolio(account);
//        String riskStockPortfolio = riskAnalyzer.getRiskStockPortfolio(account);
        System.out.println(riskStockPortfolio);
//        List<IndexData> sp500Data = globalStocksIndexMarket.getSP500Data();
//        System.out.println(sp500Data);

//        sp500Data.stream()
//        IndexCalculator riskAnalyzer = new IndexCalculator();
//        double v = riskAnalyzer.calculateAnnualMarketIndexReturn();
//        System.out.println(v);

//        List<IndexData> sp500Data = globalStocksIndexMarket.getSP500Data();
//        Map<LocalDate, Double> localDateDoubleMap = riskAnalyzer.convertToMap(sp500Data);
//
//        //середній профіт за рік
//        Map<Integer, Double> monthlyCloseMap = Calculator.getMonthlyCloseMap(localDateDoubleMap);
////        System.out.println(monthlyCloseMap.size());
//        System.out.println(monthlyCloseMap);
//        Double avgSP500 = riskAnalyzer.calculateTotalProfit(monthlyCloseMap);
//
//        Map<Integer, Double> ko = stockCalculator.getListProfitsByStock("KO");
//        Double avgKO = stockCalculator.calculateTotalProfit(ko);
////
//        System.out.println(ko);
//
//        Map<Integer, Double> aapl = stockCalculator.getListProfitsByStock("AAPL");
//        Double avgAAPL = stockCalculator.calculateTotalProfit(aapl);
//        System.out.println(aapl);
//
////        Map<Integer, Double> tsla = stockCalculator.getListProfitsByStock("TSLA");
////        Double avgTSLA = stockCalculator.calculateTotalProfit(tsla);
////        System.out.println(tsla);
//
//        Double covarianceKO = 0.0;
//        for(Integer i : monthlyCloseMap.keySet()) {
//            covarianceKO += (ko.get(i) - avgKO) * (monthlyCloseMap.get(i) - avgSP500);
//        }
//
//        Double covarianceAAPL = 0.0;
//        for(Integer i : monthlyCloseMap.keySet()) {
//            covarianceAAPL = (aapl.get(i) - avgAAPL) * (monthlyCloseMap.get(i) - avgSP500);
//        }
//
////        Double covarianceTSLA = 0.0;
////        for(Integer i : monthlyCloseMap.keySet()) {
////            covarianceTSLA = (tsla.get(i) - avgTSLA) * (monthlyCloseMap.get(i) - avgSP500);
////        }
//
//        Double covariancePortfolio = (covarianceKO + covarianceAAPL) / 2;
//
//        Double dispersion = 0.0;
//        for(Integer i : monthlyCloseMap.keySet()) {
//            dispersion += monthlyCloseMap.get(i) - (avgSP500 * avgSP500);
//        }
//        dispersion = dispersion / monthlyCloseMap.size();
//
//        Double risk = covariancePortfolio / dispersion;
//        Double riskForKO = covarianceKO / risk;
//        Double riskForAAPL = covarianceAAPL / risk;
//        System.out.println(riskForKO);
//        System.out.println(riskForAAPL);
//
//        System.out.println("Ризикованість портфелю з KO, AAPL = " + (riskForKO + riskForAAPL) / 2);
    }
}
