package com.example.javaclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaclient.R;
import com.example.javaclient.activities.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.PlaceHolder> {

    private List<String> contacts;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterContacts(ArrayList<String> contacts, Context context) {
        this.contacts = contacts;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private ImageView imgProfile;

        public PlaceHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            imgProfile = view.findViewById(R.id.imgProfile);
        }
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_contact, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final String contact = contacts.get(position);
        holder.txtName.setText((CharSequence) contact);

//        loadProfile(); //TODO add profile

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChat(contact);
            }
        });
    }

    private void openChat(String user) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
