package com.example.javaclient.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.javaclient.R;

class BroadcastDialog extends Dialog {

    EditText edtMessageBroadcast;
    ImageView imgSendBroadcast;

    public BroadcastDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_broadcast);

        edtMessageBroadcast = findViewById(R.id.edtMessageBroadcast);
        imgSendBroadcast = findViewById(R.id.imgSendBroadcast);
        imgSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(edtMessageBroadcast.getText().toString());
            }
        });
    }

    private void sendBroadcast(String message) {
        //TODO send broadcast
    }
}
