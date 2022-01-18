package com.example.pharmacyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchDrugActivity extends AppCompatActivity {
    private EditText  txtid, txtname, txtprice, txtbrandname, txtcategory;
    private Button btnserach;
    int drug_id;
    private static final String TAG = "SearchDrugActivity";
    private static final String BASE_URL = "http://192.168.1.102:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drug);
        txtid = findViewById(R.id.txt_drug_id);
        txtname = findViewById(R.id.txt_drug_name);
        txtprice = findViewById(R.id.txt_drug_price);
        txtbrandname = findViewById(R.id.txt_drug_brandname);
        txtcategory = findViewById(R.id.txt_drug_CategoryName);
        btnserach = findViewById(R.id.btn_drug_search);

        btnserach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drug_id = Integer.parseInt(txtid.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);
                Call<Drugs> call = ijsonPlaceHolder.getDrugs(drug_id);
                call.enqueue(new Callback<Drugs>() {
                    @Override
                    public void onResponse(Call<Drugs> call, Response<Drugs> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(SearchDrugActivity.this, "Invalid Drug ID :" +response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Response Error :" +response.code());
                            return;
                        }
                        Drugs drugs = response.body();
                        txtname.setText(drugs.getName());
                        txtprice.setText(Float.toString(drugs.getPrice()));
                        txtbrandname.setText(drugs.getBrand().getName());
                       txtcategory.setText(drugs.getCategory().getName());

                    }

                    @Override
                    public void onFailure(Call<Drugs> call, Throwable t) {
                        Toast.makeText(SearchDrugActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error :" +t.getMessage());

                    }
                });
            }
        });
    }
}