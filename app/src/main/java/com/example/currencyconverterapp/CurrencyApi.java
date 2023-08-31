package com.example.currencyconverterapp;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyApi {
    @GET("latest") // Modify this to match your API endpoint
    Call<CurrencyResponse> getLatestExchangeRates(String s);
}
