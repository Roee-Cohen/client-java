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
import com.example.javaclient.utils.Client;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.Commends;
import com.example.javaclient.utils.Message;
import com.example.javaclient.utils.MessagePacket;
import com.example.javaclient.utils.MessagePurpose;
import com.example.javaclient.utils.MessageType;
import com.example.javaclient.utils.RequestFormat;
import com.example.javaclient.utils.ResponseFormat;
import com.example.javaclient.utils.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private String user, me = User.getApplicationUser().getUsername();
    private ImageView imgSend;
    private EditText edtMessage;
    private ArrayList<Message> messages = new ArrayList<>();
    private AdapterChat adapterChat;
    private RecyclerView recyclerViewChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = getIntent().getStringExtra("USERNAME");
        loadViews(this);
        configureRecyclerContacts();
        ClientHandler.getInstance().setContext(this);
    }

    private void configureRecyclerContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client.getInstance().execCommand(Commends.LOAD_MESSAGES,
                        me + "," + user);
            }
        }).start();
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
                Date date = new Date();
                Message message = new Message(new MessagePacket(me, edtMessage.getText().toString(), user,
                        MessageType.MESSAGE, MessagePurpose.UNICAST), date.getHours() + ":" + date.getMinutes());
                sendMessage(message.getMessage());
                messages.add(message);
                adapterChat.notifyDataSetChanged();
                edtMessage.setText("");
            }
        });
        edtMessage = view.findViewById(R.id.edtMessage);
        recyclerViewChats = view.findViewById(R.id.recyclerViewChat);
    }

    private void sendMessage(final MessagePacket messagePacket) {
        new Thread(new Runnable() {

            ResponseFormat responseFormat;
            RequestFormat requestFormat;

            @Override
            public void run() {
                synchronized (this) {
                    requestFormat = new RequestFormat(Commends.MESSAGE, new Gson().toJson(messagePacket));
                    responseFormat = Client.getInstance().execCommand(Commends.MESSAGE,
                            Client.getInstance().getGson().toJson(requestFormat));
                    System.out.println(Client.getInstance().getGson().toJson(messagePacket) + "");
                }
            }
        }).start();
    }

    public String getUser(){
        return user;
    }

    public void updateChat(Message[] messages) {
        for (int i = 0; i < messages.length; i++) {
            this.messages.add(messages[i]);
        }
        adapterChat.notifyDataSetChanged();
    }

    public void updateChat(Message message) {
        messages.add(message);
        adapterChat.notifyDataSetChanged();
    }
}