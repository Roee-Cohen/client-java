package com.example.javaclient.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaclient.LoginActivity;
import com.example.javaclient.R;
import com.example.javaclient.utils.Client;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.Flags;
import com.example.javaclient.utils.ResponseFormat;
import com.example.javaclient.utils.Status;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    public EditText usernameEdit, passwordEdit;
    public Intent intent;
    public AsyncTask<String,Void,ResponseFormat> clientHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intent = new Intent(this, LoginActivity.class);
        usernameEdit = findViewById(R.id.usernameRegisterID);
        passwordEdit = findViewById(R.id.passwordRegisterID);
    }

    //Create an account and move to the login screen
    public void onRegisterCreateClick(View view) throws ExecutionException, InterruptedException {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        String[] args = {Flags.REGISTER.name(), username + " " + password};
        clientHandler = new ClientHandler(this,LoginActivity.URL_ADDRESS);
        clientHandler.execute(args);
    }

    //Move to the login screen
    public void onAlreadyHaveAccountClick(View view) {
        startActivity(intent);
    }

}