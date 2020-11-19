package com.example.javaclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaclient.R;
import com.example.javaclient.activities.ChatActivity;
import com.example.javaclient.activities.MainScreenActivity;
import com.example.javaclient.utils.User;

import java.util.List;

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.PlaceHolder> {

    private List<User> users;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterContacts(List<User> messageChats, Context context) {
        this.users = messageChats;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private ImageView imgConnected;
        private ImageView imgProfile;

        public PlaceHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            imgProfile = view.findViewById(R.id.imgProfile);
            imgConnected = view.findViewById(R.id.imgConnected);
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

        final User user = users.get(position);
        holder.txtName.setText(user.getUsername());

        boolean isConnected = true; //TODO user.isConnected()
//        if (isConnected)
//            holder.imgConnected.setBackgroundColor(R.color.colorBackground);
//        else
//            holder.imgConnected.setBackgroundColor(R.color.colordisconnected);

//        loadProfile(); //TODO add profile

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChat(user);
            }
        });
    }

    private void openChat(User user) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER", user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
