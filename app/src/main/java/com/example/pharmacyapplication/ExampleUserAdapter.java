package com.example.pharmacyapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExampleUserAdapter extends RecyclerView.Adapter<ExampleUserAdapter.ExampleUserViewHolder> {
    List<Example_user> userList;
    Context context;

    public ExampleUserAdapter(List<Example_user> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_user,parent,false);
        return new ExampleUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleUserViewHolder holder, int position) {
        Example_user example_user = userList.get(position);
        holder.id.setText(example_user.getId());
        holder.name.setText(example_user.getName());
        holder.username.setText(example_user.getUsername());
        holder.email.setText(example_user.getEmail());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ExampleUserViewHolder extends RecyclerView.ViewHolder{
        TextView id, name, username, email;
        public ExampleUserViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            username = itemView.findViewById(R.id.tv_username);
            email = itemView.findViewById(R.id.tv_email);
        }
    }
}
