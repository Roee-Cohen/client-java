package com.example.javaclient.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.javaclient.R;
import com.example.javaclient.utils.ClientHandler;

class BroadcastOrNewChatDialog extends Dialog {

    private boolean isBroadcast;

    private EditText edtMessageBroadcastOrNewChatName;
    private ImageView imgSendBroadcastOrNewChat;

    public BroadcastOrNewChatDialog(@NonNull Context context, boolean isBroadcast) {
        super(context);
        this.isBroadcast = isBroadcast;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_broadcast_or_new_chat);

        edtMessageBroadcastOrNewChatName = findViewById(R.id.edtMessageBroadcastOrNewChatName);
        imgSendBroadcastOrNewChat = findViewById(R.id.imgSendBroadcastOrNewChat);
        imgSendBroadcastOrNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBroadcast){
                    sendBroadcast(edtMessageBroadcastOrNewChatName.getText().toString());
                } else {
                    createNewChat(edtMessageBroadcastOrNewChatName.getText().toString());
                }
                dismiss();
            }
        });
    }

    private void createNewChat(String username) {
        ClientHandler.getInstance().createNewChat(MainScreenActivity.getUser().getUsername(), username);
    }

    private void sendBroadcast(String message) {
        ClientHandler.getInstance().sendMessage(message);
    }
}
