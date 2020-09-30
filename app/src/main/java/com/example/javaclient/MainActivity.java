package com.example.javaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.javaclient.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    public EditText usernameEdit, passwordEdit;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdit = findViewById(R.id.usernameID);
        passwordEdit = findViewById(R.id.passwordID);
    }

    public void onRegisterClick(View view) {
        intent = new Intent(this, RegisterActivity.class);


        startActivity(intent);
    }
}