package com.example.currencyconverterapp;

public class CountryItem {
    String countryName;
    int countryFlag;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    public CountryItem(String countryName, int countryFlag) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;

    }
}
