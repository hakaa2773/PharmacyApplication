package com.example.pharmacyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.favre.lib.crypto.bcrypt.BCrypt;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtname,txtphone, txtemail, txtpassword;
    private Button btnregister;
    private TextView tvregister;
    private TextView tvlink;
    private static final String TAG ="RegisterActivity";
    private static final String BASE_URL = "http://192.168.1.100:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtname = findViewById(R.id.txtCustomerName);
        txtphone = findViewById(R.id.txtCustomerPhone);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        btnregister = findViewById(R.id.btnregister);
        tvregister = findViewById(R.id.tvregister);
        tvlink = findViewById(R.id.tvregister);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);
        tvlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int_reg = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(int_reg);
                finish();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,phone,email,password;
                name = txtname.getText().toString();
                phone = txtphone.getText().toString();
                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();

                if (name.equals("")||phone.equals("")||email.equals("")||password.equals("")){
                    Toast.makeText(RegisterActivity.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                    return;
                }
                String hashpassword= BCrypt.withDefaults().hashToString(12,password.toCharArray());

                CustomerLogin customerLogin = new CustomerLogin(email,hashpassword);
                Customer customer = new Customer(name,phone,email,customerLogin);

                Call<ResponseBody> call = ijsonPlaceHolder.savecustomer(customer);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register Fail :"+response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Response Error :"+response.code());
                            return;
                        }
                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Register Fail :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,t.getMessage());

                    }
                });
            }
        });
    }



}