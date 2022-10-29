package com.ass2.i190582_i190534;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    ArrayList<Person> ls;
    ArrayList<Person> itemsCopy;
    Context c;
    public ContactsAdapter(ArrayList<Person> ls, Context c) {
        this.ls = ls;
        this.c = c;
        this.itemsCopy = new ArrayList<>();
        this.itemsCopy.addAll(ls);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.phone_number.setText(ls.get(position).getPhone_number());
    }

    // To search contacts
    public void filter(String text) {
        ls.clear();
        if(text.isEmpty()){
            ls.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(Person person: itemsCopy){
                if(person.name.toLowerCase().contains(text) || person.phone_number.toLowerCase().contains(text)){
                    ls.add(person);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone_number = itemView.findViewById(R.id.phone_number);
        }
    }
}

