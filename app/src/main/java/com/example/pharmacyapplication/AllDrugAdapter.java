package com.example.pharmacyapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllDrugAdapter extends RecyclerView.Adapter<AllDrugAdapter.AllDrugViewHolder> {
    List<DrugAll> allDrugs;
    Context context;

    public AllDrugAdapter(List<DrugAll> allDrugs, Context context) {
        this.allDrugs = allDrugs;
        this.context = context;
    }

    @NonNull
    @Override
    public AllDrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drugdetails,parent,false);
        return  new AllDrugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDrugViewHolder holder, int position) {
        DrugAll drugAll = allDrugs.get(position);
        holder.name.setText(drugAll.getDrugname());
        holder.brand.setText(drugAll.getBrandname());
        holder.category.setText(drugAll.getCategoryname());
        holder.price.setText(drugAll.getDrugprice().toString());

    }

    @Override
    public int getItemCount() {
       return allDrugs.size();
    }

    public class AllDrugViewHolder extends  RecyclerView.ViewHolder{
        TextView name,brand,category,price;

        public AllDrugViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.drug_name);
            brand = itemView.findViewById(R.id.drug_brand);
            category = itemView.findViewById(R.id.drug_category);
            price = itemView.findViewById(R.id.drug_price);
        }
    }


}
