package com.stock.helper;

import java.util.Random;

public class AccountHelper {
    public static String generateAccountNumber(){
        int accountNumber;
        Random random = new Random();
        int bound = 1000;
        accountNumber = bound * random.nextInt(bound);
        return Integer.toString(accountNumber);
    }
}
