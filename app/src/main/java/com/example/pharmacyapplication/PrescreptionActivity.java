package com.example.pharmacyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrescreptionActivity extends AppCompatActivity {
    private EditText txtemail,txtphone, txtaddress, txtdrug;
    private Button btnadd,btncancel;
    private LinearLayout linearLayout;
    private static final String TAG ="RegisterActivity";
    private static final String BASE_URL = "http://192.168.1.106:8080";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescreption);
        txtemail = findViewById(R.id.txtpreemail);
        txtphone = findViewById(R.id.txtprephone);
        txtaddress = findViewById(R.id.txtpreaddress);
        txtdrug = findViewById(R.id.txtpredrug);
        btnadd = findViewById(R.id.btnadd);
        btncancel = findViewById(R.id.btn_back);
        linearLayout = findViewById(R.id.lineupdateprescription);
        String email = "muditha@gmail.com";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);

       Call<Customer> call = ijsonPlaceHolder.checkCustomerEmail(email);
       call.enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (!response.isSuccessful()){}
                            Customer customer = response.body();
                            txtemail.setText(customer.getEmail());
                            txtphone.setText(customer.getPhone());

                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {

                        }
                    });


               linearLayout.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       startActivity(new Intent(PrescreptionActivity.this, SearchPrescriptionActivity.class));

                   }
               });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrescreptionActivity.this, MainActivity.class));
            }
        });



        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,phone,address,drugs;
                email = txtemail.getText().toString();
                phone = txtphone.getText().toString();
                address = txtaddress.getText().toString();
                drugs = txtdrug.getText().toString();

                if (email.equals("")||phone.equals("")||address.equals("")||drugs.equals("")){
                    Toast.makeText(PrescreptionActivity.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Prescription prescription = new Prescription(email,hashpassword);
                Prescription prescription = new Prescription(email,phone,address,drugs);
                Call<ResponseBody> call = ijsonPlaceHolder.saveprescription(prescription);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(PrescreptionActivity.this, "Register Fail :"+response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Response Error :"+response.code());
                            return;
                        }
                        Toast.makeText(PrescreptionActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        CTRLMgt();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(PrescreptionActivity.this, "Register Fail :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,t.getMessage());

                    }
                });
            }
        });
    }
    void CTRLMgt(){
        txtemail.setText("");
        txtphone.setText("");
        txtaddress.setText("");
        txtdrug.setText("");

    }
}