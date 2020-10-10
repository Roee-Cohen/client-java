package com.example.javaclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaclient.R;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.Flags;
import com.example.javaclient.utils.ResponseFormat;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    public EditText usernameEdit, passwordEdit, passwordConfirmEdit;
    public Intent intent;
    public AsyncTask<String,Void,ResponseFormat> clientHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intent = new Intent(this, LoginActivity.class);
        usernameEdit = findViewById(R.id.edtUsernameID);
        passwordEdit = findViewById(R.id.edtPasswordID);
        passwordConfirmEdit = findViewById(R.id.edtPasswordConfirmID);
    }

    //Create an account and move to the login screen
    public void onRegisterCreateClick(View view) throws ExecutionException, InterruptedException {
        String URL_ADDRESS = getResources().getString(R.string.LOCALHOST_IP);
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String passwordConfirm = passwordConfirmEdit.getText().toString();

        if(password.equals(passwordConfirm)) {
            String[] args = {Flags.REGISTER.name(), username + " " + password};
            clientHandler = new ClientHandler(this, URL_ADDRESS);
            clientHandler.execute(args);
        } else {
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        }
    }

    //Move to the login screen
    public void onAlreadyHaveAccountClick(View view) {
        startActivity(intent);
    }

}