package com.example.pharmacyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExampleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static final String TAG ="Example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        recyclerView = findViewById(R.id.rec_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);
        Call<List<Example_user>> call = ijsonPlaceHolder.getUsers();

        call.enqueue(new Callback<List<Example_user>>() {
            @Override
            public void onResponse(Call<List<Example_user>> call, Response<List<Example_user>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(ExampleActivity.this, "Error :"+response.code(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,"API Response Error : "+response.code());
                        return;
                    }
                    else {
                        List<Example_user> exampleUserList = response.body();
                        ExampleUserAdapter exampleUserAdapter = new ExampleUserAdapter(exampleUserList, ExampleActivity.this);
                        recyclerView.setAdapter(exampleUserAdapter);
                    }
            }

            @Override
            public void onFailure(Call<List<Example_user>> call, Throwable t) {
                Toast.makeText(ExampleActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}