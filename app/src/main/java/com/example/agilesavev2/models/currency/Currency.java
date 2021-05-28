package com.example.agilesavev2.models.currency;

public class Currency {
    String currency;
    double conversionRate;

    public Currency(String currency, double conversionRate) {
        this.currency = currency;
        this.conversionRate = conversionRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }
}


