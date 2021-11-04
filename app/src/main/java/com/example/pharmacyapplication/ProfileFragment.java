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

public class ProfileFragment extends Fragment {
    private View view;
    private EditText txtfirstname,txtlastname, txtphone, txtemail, txtaddress;
    private Button btnsignout, btnedit;
    private FirebaseAuth authentication;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private ProgressDialog pd;
    private LinearLayout lin_slider1;
    private final String TAG = "profile Fragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnsignout = view.findViewById(R.id.btnsignout);
        txtfirstname = view.findViewById(R.id.pro_fistname);
        txtlastname = view.findViewById(R.id.pro_lastname);
        txtphone = view.findViewById(R.id.pro_phone);
        txtemail = view.findViewById(R.id.pro_Email);
        txtaddress = view.findViewById(R.id.pro_address);
        btnedit = view.findViewById(R.id.pro_btnedit);
        authentication = FirebaseAuth.getInstance();
        user = authentication.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(getContext());



        loadprofile(user);


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
                String label = btnedit.getText().toString();
                if (label.equals("Edit Profile")) {
                    CTRL_MGT(true);
                    btnedit.setText("SAVE PROFILE");
                } else if (label.equals("SAVE PROFILE")) {
                    saveProfile(user);

                }

            }
        });
        return view;
    }

    private void saveProfile(FirebaseUser user) {
        String firstname = txtfirstname.getText().toString();
        String lastname = txtlastname.getText().toString();
        String email = txtemail.getText().toString();
        String phone = txtphone.getText().toString();
        String address = txtaddress.getText().toString();
        String uid = user.getUid();

        if (firstname.equals("") ||lastname.equals("") || email.equals("") || phone.equals("") || address.equals("")) {
            Toast.makeText(getContext(), "fill the text field ", Toast.LENGTH_SHORT).show();
        } else {
            Customer cust = new Customer();
            cust.setCustid(uid);
            cust.setCustfirstname(firstname);
            cust.setCustlastname(lastname);
            cust.setCustemail(email);
            cust.setCustphone(phone);
            cust.setCustaddress(address);

            dbRef.child("customer").child(uid).setValue(cust, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        CTRL_MGT(false);
                        btnedit.setText("EDIT PROFILE");
                        Toast.makeText(getContext(), "successfully saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Errorr", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "record Update error" + error.getMessage());
                    }
                }
            });

        }
    }

    private void loadprofile(FirebaseUser user) {
        Toast.makeText(getContext(), user.getUid(), Toast.LENGTH_SHORT).show();
        pd.setMessage("Loading");
        pd.show();
        String uid = user.getUid();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer cust = snapshot.child("customer").child(uid).getValue(Customer.class);
                txtfirstname.setText(cust.getCustfirstname());
                txtlastname.setText(cust.getCustlastname());
                txtphone.setText(cust.getCustphone());
                txtemail.setText(cust.getCustemail());
                txtaddress.setText(cust.getCustaddress());
                CTRL_MGT(false);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "DB Error:" + error.getMessage().toString());

            }
        });
    }

    private void CTRL_MGT(boolean status) {
        txtfirstname.setEnabled(status);
        txtlastname.setEnabled(status);
        txtphone.setEnabled(status);
        txtemail.setEnabled(status);
        txtaddress.setEnabled(status);


    }
}