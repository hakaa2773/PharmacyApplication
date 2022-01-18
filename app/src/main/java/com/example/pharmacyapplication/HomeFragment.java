package com.example.pharmacyapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
    private View view;
    private LinearLayout linearLayout1, linearLayout2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        linearLayout1 = view.findViewById(R.id.aboutus);
        linearLayout2 = view.findViewById(R.id.allproducts);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AllDrugsActivity.class));

            }
        });
        return view;
    }

}