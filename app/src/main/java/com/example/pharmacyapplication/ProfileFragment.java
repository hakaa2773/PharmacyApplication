package com.example.pharmacyapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private View view;
    private EditText txtname,txtemail, txtphone;
    private Button btnsignout, btnedit;
    private final String TAG = "profile Fragment";
    private static final String BASE_URL = "http://20.7.3.47:8080";
    private String email;



    public ProfileFragment(String email) {
        this.email=email;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnsignout = view.findViewById(R.id.btnsignout);
        txtname = view.findViewById(R.id.pro_fistname);
        txtemail = view.findViewById(R.id.pro_Email);
        txtphone = view.findViewById(R.id.pro_phone);
        btnedit = view.findViewById(R.id.pro_btnedit);

       String email = this.email;

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
                txtname.setText(customer.getName());
                txtemail.setText(customer.getEmail());
                txtphone.setText(customer.getPhone());

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });





        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }











    private void CTRL_MGT(boolean status) {
        txtname.setEnabled(status);
        txtphone.setEnabled(status);
        txtemail.setEnabled(status);


    }
}