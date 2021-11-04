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

public class RegisterActivity extends AppCompatActivity {
    private EditText txtfirstname,txtlastname, txtphone, txtemail, txtpassword;
    private Button btnregister;
    private TextView tvregister;
    FirebaseAuth authentication;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtfirstname = findViewById(R.id.txtFirstName);
        txtlastname = findViewById(R.id.txtLastName);
        txtphone = findViewById(R.id.txtphone);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        btnregister = findViewById(R.id.btnregister);
        tvregister = findViewById(R.id.tvregister);
        authentication = FirebaseAuth.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference();

        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname, lastname, phone, email, password, address;
                firstname = txtfirstname.getText().toString();
                lastname = txtlastname.getText().toString();
                phone = txtphone.getText().toString();
                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();

                if (firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "please fill all the text fields", Toast.LENGTH_SHORT).show();
                } else {
                    createUser(email, password, firstname, lastname, phone);
                }
            }

        });

    }

            private void createUser(String email, String password, String firstname, String lastname, String phone) {
                authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("RegisterActivity", "Successfully Registered");
                            String custID = authentication.getCurrentUser().getUid();
                            createcustomer(custID, firstname,lastname, email, phone);
                        }else {
                            Toast.makeText(RegisterActivity.this, "Registration Failure", Toast.LENGTH_SHORT).show();
                            Exception e = task.getException();
                            Log.e("RegisterActivity", e.getMessage());
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Registration Fail Try Again", Toast.LENGTH_SHORT).show();
                        Log.e("RegisterActivity", e.getMessage());

                    }
                });
            }


    private void createcustomer(String custID, String firstname, String lastname, String email, String phone) {
        db_ref = FirebaseDatabase.getInstance().getReference();
        Customer customer = new Customer(custID, firstname,lastname, email, phone);
        db_ref.child("customer").child(custID).setValue(customer);
        Toast.makeText(this, "added to the database ", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}