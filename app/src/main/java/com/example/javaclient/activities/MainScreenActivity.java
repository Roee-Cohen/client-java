package com.example.javaclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.javaclient.R;
import com.example.javaclient.utils.User;

public class MainScreenActivity extends AppCompatActivity {

    //Android
    private Intent intent;
    private TextView mainHelloView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        intent = getIntent();
        mainHelloView = findViewById(R.id.mainViewID);

        user = (User) intent.getSerializableExtra("user");

        if(user == null) {
            mainHelloView.setText("Hello $$$$");
        } else {
            mainHelloView.setText("Hello " + user.getUsername() + "!");
        }
    }
}