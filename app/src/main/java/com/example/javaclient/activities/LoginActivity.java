package com.example.javaclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.javaclient.R;
import com.example.javaclient.activities.RegisterActivity;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.Flags;
import com.example.javaclient.utils.ResponseFormat;

public class LoginActivity extends FragmentActivity {
    //10.0.2.2

    private AsyncTask<String,Void,ResponseFormat> clientHandler;
    private EditText usernameEdit, passwordEdit;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdit = findViewById(R.id.edtUsername);
        passwordEdit = findViewById(R.id.edtPassLogin);
    }

    public void onLoginClick(View view) {
        String URL_ADDRESS = getResources().getString(R.string.LOCALHOST_IP);
        final String username = usernameEdit.getText().toString();
        final String password = passwordEdit.getText().toString();

        String[] args = {Flags.LOGIN.name(), username + " " + password};
        clientHandler = new ClientHandler(this,URL_ADDRESS, false);
        clientHandler.execute(args);
    }

    public void onRegisterClick(View view) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}