package com.example.javaclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaclient.R;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.Commends;
import com.example.javaclient.utils.ResponseFormat;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences storage;
    private EditText usernameEdit, passwordEdit;
    private Button btnLogin;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = getApplicationContext().getSharedPreferences("Storage", Context.MODE_PRIVATE);
        usernameEdit = findViewById(R.id.edtUsername);
        passwordEdit = findViewById(R.id.edtPassLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                commitLogin(username, password);
                showTopToast("Welcome back, " + username + "!");
            }
        });
    }

    private void showTopToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    private void commitLogin(String username, String password) {
        String[] args = {Commends.LOGIN.name(), username + " " + password};
        ClientHandler.getInstance().setContext(this);
        if (ClientHandler.canRun)
            ClientHandler.getInstance().execute(args);
    }

    public void onLoginClick(View view) {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        commitLogin(username, password);
    }

    public void onRegisterClick(View view) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}