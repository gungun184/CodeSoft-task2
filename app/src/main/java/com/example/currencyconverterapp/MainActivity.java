package com.example.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.os.Bundle;
import java.util.Map;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CurrencyApi currencyApi;

    EditText enterAmount;
    Button btnconvert;
    TextView convertedAmount;
    CountryItem countryItem;
    String clickedCountryName;
    CountryItem countryItemTo;
    String clickedCountryNameTo;

    ///////////////////////////////
    private ArrayList<CountryItem> countryList;
    private CountryAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangerate.host/latest/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currencyApi = retrofit.create(CurrencyApi.class);
        iniListCountry();

        btnconvert = findViewById(R.id.converter_btn);
        enterAmount = findViewById(R.id.amount_edit_text);
        convertedAmount = findViewById(R.id.converted_amount);
        Button refreshButton = findViewById(R.id.refresh_btn);
        Spinner spinnerCountries = findViewById(R.id.spinner_countries);
        Spinner spinnerCountriesTo = findViewById(R.id.spinner_countries_to);

        mAdapter = new CountryAdapter(this,countryList);
        spinnerCountries.setAdapter(mAdapter);
        spinnerCountriesTo.setAdapter(mAdapter);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear input fields and reset converted amount
                EditText amountEditText = findViewById(R.id.amount_edit_text);
                TextView convertedAmountTextView = findViewById(R.id.converted_amount);

                amountEditText.setText("");
                convertedAmountTextView.setText("0.000"); // Reset to default value
            }
        });
        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertedAmount.setText("");
                countryItem =(CountryItem) parent.getItemAtPosition(position);
                clickedCountryName = countryItem.getCountryName();
                Toast.makeText(MainActivity.this,clickedCountryName + "Selected",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinnerCountriesTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertedAmount.setText("");
                countryItemTo =(CountryItem) parent.getItemAtPosition(position);
                clickedCountryNameTo = countryItemTo.getCountryName();
                Toast.makeText(MainActivity.this,clickedCountryNameTo + " Selected ",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        btnconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountStr = enterAmount.getText().toString();
                if (amountStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double totalConvertedAmount;
                Double amount = Double.parseDouble(enterAmount.getText().toString());

                if (clickedCountryName.equals("India")) {
                    if (clickedCountryNameTo.equals("USA")) {
                        totalConvertedAmount = amount * 0.012;
                    } else if (clickedCountryNameTo.equals("Vietnam")) {
                        totalConvertedAmount = amount * 291.37;
                    } else if (clickedCountryNameTo.equals("Pakistan")) {
                        totalConvertedAmount = amount * 3.67;
                    } else {
                        totalConvertedAmount = amount; // Same currency
                    }
                } else if (clickedCountryName.equals("USA")) {
                    if (clickedCountryNameTo.equals("India")) {
                        totalConvertedAmount = amount * 82.68;
                    } else if (clickedCountryNameTo.equals("Vietnam")) {
                        totalConvertedAmount = amount * 24090.00;
                    } else if (clickedCountryNameTo.equals("Pakistan")) {
                        totalConvertedAmount = amount * 82.58;
                    } else {
                        totalConvertedAmount = amount; // Same currency
                    }
                } else if (clickedCountryName.equals("Vietnam")) {
                    if (clickedCountryNameTo.equals("India")) {
                        totalConvertedAmount = amount * 0.0034;
                    } else if (clickedCountryNameTo.equals("USA")) {
                        totalConvertedAmount = amount * 0.000042;
                    } else if (clickedCountryNameTo.equals("Pakistan")) {
                        totalConvertedAmount = amount * 0.013;
                    } else {
                        totalConvertedAmount = amount; // Same currency
                    }
                } else if (clickedCountryName.equals("Pakistan")) {
                    if (clickedCountryNameTo.equals("India")) {
                        totalConvertedAmount = amount * 0.27;
                    } else if (clickedCountryNameTo.equals("USA")) {
                        totalConvertedAmount = amount * 0.0033;
                    } else if (clickedCountryNameTo.equals("Vietnam")) {
                        totalConvertedAmount = amount * 79.42;
                    } else {
                        totalConvertedAmount = amount; // Same currency
                    }} else {
                    totalConvertedAmount = amount; // Default case
                }


                String tot = String.format("%.2f", totalConvertedAmount);
                convertedAmount.setText(tot);
            }
        });

    }
    private void fetchExchangeRates() {
        Call<CurrencyResponse> call = currencyApi.getLatestExchangeRates("7b8eee2e6f9add3317b45634f8ff6215");
        call.enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                if (response.isSuccessful()) {
                    CurrencyResponse currencyResponse = response.body();
                    if (currencyResponse != null) {
                        // Process the response and update your currency data
                        Map<String, Double> exchangeRates = currencyResponse.getRates();
                        // Store or update your exchange rates data
                    }
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                // Handle network error
            }
        });
    }


    private void iniListCountry() {
        countryList = new ArrayList<>();
        countryList.add(new CountryItem("India",R.drawable.img_1));
        countryList.add(new CountryItem("Vietnam",R.drawable.img_2));
        countryList.add(new CountryItem("USA",R.drawable.img_3));
        countryList.add(new CountryItem("Pakistan",R.drawable.img));


    }
}