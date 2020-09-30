package com.example.javaclient.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.javaclient.LoginActivity;
import com.example.javaclient.R;

public class RegisterActivity extends AppCompatActivity {

    public EditText usernameEdit, passwordEdit;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intent = new Intent(this, LoginActivity.class);
        usernameEdit = findViewById(R.id.usernameRegisterID);
        passwordEdit = findViewById(R.id.passwordRegisterID);
    }

    //Create an account and move to the login screen
    public void onRegisterCreateClick(View view) {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();



        startActivity(intent);
    }

    //Move to the login screen
    public void onAlreadyHaveAccountClick(View view) {
        startActivity(intent);
    }

}