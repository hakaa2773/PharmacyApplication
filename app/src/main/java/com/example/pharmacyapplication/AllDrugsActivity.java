package com.example.pharmacyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllDrugsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static final String TAG ="AllDrugsActivity";
    private static final String BASE_URL = "http://192.168.1.100:8080";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drugs);
        recyclerView =findViewById(R.id.rcy_getall_drugs);

        recyclerView.setLayoutManager(new LinearLayoutManager(AllDrugsActivity.this));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);
        Call<List<DrugAll>> call = ijsonPlaceHolder.getAllDrugs();
        call.enqueue(new Callback<List<DrugAll>>() {
            @Override
            public void onResponse(Call<List<DrugAll>> call, Response<List<DrugAll>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(AllDrugsActivity.this, "Error"+response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"Error :"+ response.code());
                    return;
                }
                else {
                    List<DrugAll> drugList = response.body();
                    AllDrugAdapter allDrugAdapter = new AllDrugAdapter(drugList,getApplicationContext());
                    recyclerView.setAdapter(allDrugAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<DrugAll>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "tyt6ut", Toast.LENGTH_SHORT).show();

            }
        });

    }
}