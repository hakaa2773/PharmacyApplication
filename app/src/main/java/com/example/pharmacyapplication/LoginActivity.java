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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText txtemail, textpassword;
    private Button btnlogin;
    private TextView tvlink;
    private static final String TAG ="RegisterActivity";
    private static final String BASE_URL = "http://20.7.3.47:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtemail = findViewById(R.id.txtemail);
        textpassword = findViewById(R.id.txtpassword);
        btnlogin = findViewById(R.id.btnlogin);
        tvlink = findViewById(R.id.tvlink);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email = txtemail.getText().toString();
                password = textpassword.getText().toString();

                if (email.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Pleas enter password or email", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<CustomerLogin> call = ijsonPlaceHolder.checkCustomer(email);
                call.enqueue(new Callback<CustomerLogin>() {
                    @Override
                    public void onResponse(Call<CustomerLogin> call, Response<CustomerLogin> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "invalid username password", Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Invalid data :"+response.code());
                            return;
                        }
                        CustomerLogin customerLogin = response.body();
                        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(),customerLogin.getPassword());
                        if (result.verified == false){
                            Toast.makeText(LoginActivity.this, "invalid username password", Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Invalid password :"+response.code());
                            return;
                        }
                        else if (customerLogin.getStatus()!=1){
                            Toast.makeText(LoginActivity.this, "your account has been disabled", Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"block account");
                            return;
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Valid Customer", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("email", email);
                            Toast.makeText(LoginActivity.this, email, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

                        
                    }

                    @Override
                    public void onFailure(Call<CustomerLogin> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,t.getMessage());

                    }
                });



            }
        });
        tvlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int_reg = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(int_reg);
                finish();
            }
        });
    }


}