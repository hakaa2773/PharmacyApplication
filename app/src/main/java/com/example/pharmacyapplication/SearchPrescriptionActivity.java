package com.example.pharmacyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPrescriptionActivity extends AppCompatActivity {
    private EditText txtid, txtemail, txtphone, txtaddress, txtdrug;
    private Button btnserach,btnupdate,btndelete, btncancel;
    int prescription_id;
    private static final String TAG = "SearchPrescriptionActivity";
    private static final String BASE_URL = "http://20.7.3.47:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prescription);

        txtid = findViewById(R.id.prescription_id);
        txtemail = findViewById(R.id.txt_Prescription_email);
        txtphone = findViewById(R.id.txt_prescription_phone);
        txtaddress = findViewById(R.id.txt_Prescription_address);
        txtdrug = findViewById(R.id.txt_Prescription_drugs);
        btnserach = findViewById(R.id.btn_prescription_search);
        btnupdate = findViewById(R.id.btn_prescription_update);
        btndelete = findViewById(R.id.btn_prescription_delete);
        btncancel = findViewById(R.id.btn_cancel1);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IJSONPlaceHolder ijsonPlaceHolder = retrofit.create(IJSONPlaceHolder.class);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchPrescriptionActivity.this, MainActivity.class));
            }
        });


        btnserach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescription_id = Integer.parseInt(txtid.getText().toString());
                //CTRLMgt();

                Call<Prescription> call = ijsonPlaceHolder.getprescription(prescription_id);
                call.enqueue(new Callback<Prescription>() {
                    @Override
                    public void onResponse(Call<Prescription> call, Response<Prescription> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(SearchPrescriptionActivity.this, "Invalid Prescription ID :" +response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Response Error :" +response.code());
                            return;
                        }
                        Prescription prescription = response.body();
                        txtid.setText(String.valueOf(prescription.getId()));
                        txtemail.setText(prescription.getEmail());
                        txtphone.setText(prescription.getPhone());
                        txtaddress.setText(prescription.getAddress());
                        txtdrug.setText(prescription.getDrugs());
                    }

                    @Override
                    public void onFailure(Call<Prescription> call, Throwable t) {
                        Toast.makeText(SearchPrescriptionActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error :" +t.getMessage());

                    }
                });
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id;
                String name,phone,address,drugs;
                if (!txtid.getText().toString().equals(""))
                    id = Integer.parseInt(txtid.getText().toString());
                else {
                    Toast.makeText(SearchPrescriptionActivity.this, "Please enter Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                name = txtemail.getText().toString();
                phone = txtphone.getText().toString();
                address = txtaddress.getText().toString();
                drugs = txtdrug.getText().toString();

                if (name.equals("")||phone.equals("")||address.equals("")||drugs.equals("")){
                    Toast.makeText(SearchPrescriptionActivity.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                    return;
                }
                Prescription prescription = new Prescription();
                prescription.setEmail(name);
                prescription.setPhone(phone);
                prescription.setAddress(address);
                prescription.setDrugs(drugs);
                Call<ResponseBody> call = ijsonPlaceHolder.Updateprescription(id,prescription);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(SearchPrescriptionActivity.this, "Register Fail :"+response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Response Error :"+response.code());
                            return;
                        }
                        Toast.makeText(SearchPrescriptionActivity.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                        CTRLMgt();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SearchPrescriptionActivity.this, "Register Fail :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,t.getMessage());

                    }
                });

            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id;
                if (!txtid.getText().toString().equals(""))
                    id = Integer.parseInt(txtid.getText().toString());
                else {
                    Toast.makeText(SearchPrescriptionActivity.this, "Please enter Id", Toast.LENGTH_SHORT).show();
                    return;

                }
                Call<ResponseBody> call = ijsonPlaceHolder.deletePrescription(id);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(SearchPrescriptionActivity.this, "Errorrr :"+response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Response Error :"+response.code());
                            return;
                        }
                        Toast.makeText(SearchPrescriptionActivity.this, "Deleted Successfully ", Toast.LENGTH_SHORT).show();
                        CTRLMgt();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SearchPrescriptionActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,t.getMessage());

                    }
                });

            }
        });
    }
    void CTRLMgt(){
        txtid.setText("");
        txtemail.setText("");
        txtphone.setText("");
        txtaddress.setText("");
        txtdrug.setText("");

    }
}