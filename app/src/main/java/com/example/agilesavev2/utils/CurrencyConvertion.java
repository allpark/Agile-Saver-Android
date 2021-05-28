package com.example.agilesavev2.utils;

import com.example.agilesavev2.services.Services;

public class CurrencyConvertion {
    private static double currency;
    private static String currencyType;

    public CurrencyConvertion(double currency, String currencyType) {
        this.currency = currency;
        this.currencyType = currencyType;
    }

    public double getConvertedCurrency(){
        return currency * Services.getCurrencyConversionRate(currencyType);
    }

    public static double getConvertedCurrency(String currencyType, double currency){
        System.out.println("CONVERTED  "+currency+" / "+Services.getCurrencyConversionRate(currencyType)+" = "+currency / Services.getCurrencyConversionRate(currencyType));
        return Math.round((currency / Services.getCurrencyConversionRate(currencyType))*100 )/100;
    }
}
