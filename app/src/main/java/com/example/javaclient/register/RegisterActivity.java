package com.example.javaclient.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaclient.LoginActivity;
import com.example.javaclient.R;
import com.example.javaclient.utils.Client;
import com.example.javaclient.utils.Flags;
import com.example.javaclient.utils.ResponseFormat;
import com.example.javaclient.utils.Status;

public class RegisterActivity extends AppCompatActivity {

    public EditText usernameEdit, passwordEdit;
    public Client client;
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

        client = new Client("127.0.0.1");
        new Thread(client).start();
//        ResponseFormat response = client.ExecCommand(Flags.REGISTER, username + " " + password);
//        if(response.status.equals(Status.OK)) {
//            Toast.makeText(this, "User created!", Toast.LENGTH_SHORT);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Error: " + response.status.name(), Toast.LENGTH_SHORT);
//        }
    }

    //Move to the login screen
    public void onAlreadyHaveAccountClick(View view) {
        startActivity(intent);
    }

}