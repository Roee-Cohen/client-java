package com.example.javaclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.javaclient.R;
import com.example.javaclient.adapters.AdapterChat;
import com.example.javaclient.adapters.AdapterContacts;
import com.example.javaclient.utils.MessagePacket;
import com.example.javaclient.utils.User;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private User user;
    private ImageView imgSend;
    private EditText edtMessage;
    private ArrayList<MessagePacket> messages = new ArrayList<>();
    private AdapterChat adapterChat;
    private RecyclerView recyclerViewChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = (User) getIntent().getSerializableExtra("USER");
        loadViews(this);
        configureRecyclerContacts();
    }

    private void configureRecyclerContacts() {
        messages = new ArrayList();
        messages.add(new MessagePacket("me", "hi", user.getUsername()));
        messages.add(new MessagePacket(user.getUsername(), "hello", "me"));
        adapterChat = new AdapterChat(messages, this);
        recyclerViewChats.setHasFixedSize(true);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChats.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewChats.setAdapter(adapterChat);
    }

    private void loadViews(AppCompatActivity view) {
        imgSend = view.findViewById(R.id.imgSend);
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        edtMessage = view.findViewById(R.id.edtMessage);
        recyclerViewChats = view.findViewById(R.id.recyclerViewChat);
    }

    private void sendMessage() {
        //TODO send message
    }

}