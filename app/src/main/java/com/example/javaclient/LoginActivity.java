package com.example.javaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.javaclient.register.RegisterActivity;
import com.example.javaclient.utils.Client;
import com.example.javaclient.utils.Flags;

public class LoginActivity extends AppCompatActivity {

    private Client client;

    private EditText usernameEdit, passwordEdit;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdit = findViewById(R.id.usernameID);
        passwordEdit = findViewById(R.id.passwordID);
        client = new Client("localhost");
    }

    public void onRegisterClick(View view) {
        intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }
}