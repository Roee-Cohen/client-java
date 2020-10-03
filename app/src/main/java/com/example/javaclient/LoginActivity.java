package com.example.javaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaclient.register.RegisterActivity;
import com.example.javaclient.utils.Client;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.Flags;
import com.example.javaclient.utils.ResponseFormat;
import com.example.javaclient.utils.Status;

public class LoginActivity extends AppCompatActivity {
    //10.0.2.2

    public static final String URL_ADDRESS = "10.0.2.2";

    private AsyncTask<String,Void,ResponseFormat> clientHandler;
    private EditText usernameEdit, passwordEdit;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdit = findViewById(R.id.usernameID);
        passwordEdit = findViewById(R.id.passwordID);
    }

    public void onLoginClick(View view) {
        final String username = usernameEdit.getText().toString();
        final String password = passwordEdit.getText().toString();

        String[] args = {Flags.LOGIN.name(), username + " " + password};
        clientHandler = new ClientHandler(this,URL_ADDRESS);
        clientHandler.execute(args);
    }

    public void onRegisterClick(View view) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}