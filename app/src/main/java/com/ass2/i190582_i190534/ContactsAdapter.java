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
    Context c;
    Intent intent;
    public ContactsAdapter(ArrayList<Person> ls, Context c) {
        this.ls = ls;
        this.c = c;

    }
    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts, parent, false);
        return new com.ass2.i190582_i190534.ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
      //      holder.name.setText(ls.getName());
      //      holder.phone_number.setText(Person.getPhone_number());

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

//    public ContactsAdapter(@NonNull FirebaseRecyclerOptions<Person> options) {
//        super(options);
//    }
//
//
//
//        @NonNull
//        @Override
//        public com.ass2.i190582_i190534.ContactsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts, parent, false);
//            return new com.ass2.i190582_i190534.ContactsAdapter.myViewHolder(view);
//        }
//
//        @Override
//        protected void onBindViewHolder(@NonNull com.ass2.i190582_i190534.ContactsAdapter.myViewHolder holder, int position, @NonNull Person model) {
////        holder.image.setText(model.getImage());
//            holder.name.setText(model.getName());
//            holder.phone_number.setText(model.getPhone_number());
//        }
//
//        class myViewHolder extends RecyclerView.ViewHolder{
//            TextView name, phone_number;
//
//            public myViewHolder(@NonNull View itemView) {
//                super(itemView);
//
//               name = itemView.findViewById(R.id.name);
//               phone_number = itemView.findViewById(R.id.phone_number);
//
//            }
//        }
//    }


