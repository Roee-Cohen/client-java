package com.example.javaclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.javaclient.activities.MainScreenActivity;

public class ClientHandler extends AsyncTask<String, Void, ResponseFormat> {

    private String address;
    private Client client;
    private Context context;
    private Flags currentFlag;
    private User currentUser;

    public ClientHandler(Context context, String address) {
        this.address = address;
        this.context = context;
        currentUser = null;
        client = null;
    }

    @Override
    protected ResponseFormat doInBackground(String... voids) {
        Flags flag = Flags.valueOf(voids[0].toUpperCase());
        String secondArg = voids[1];
        String username = secondArg.split(" ")[0];
        String password = secondArg.split(" ")[1];
        currentFlag = flag;
        client = new Client(address);
        currentUser = new User(username, password);
        ResponseFormat response = client.ExecCommand(flag, secondArg);
        return response;
    }

    @Override
    protected void onPostExecute(ResponseFormat response) {
        com.example.javaclient.utils.Status status = response.status;
        if(status.equals(com.example.javaclient.utils.Status.OK)) {
            Toast.makeText(context, currentFlag.getMessage(), Toast.LENGTH_SHORT).show();
            makeAction(currentFlag);
        } else if(status.equals(com.example.javaclient.utils.Status.NOTFOUND)) {
            Toast.makeText(context, "User not found!", Toast.LENGTH_SHORT).show();
        } else if(status.equals(com.example.javaclient.utils.Status.BAD_REQUEST)) {
            Toast.makeText(context, "User already exists!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "There seems to be an error connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeAction(Flags flag) {
        switch(flag) {
            case LOGIN: {
                //Save data to shared preferences
                SharedPreferences storage = context.getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("username", currentUser.getUsername());
                editor.putString("password", currentUser.getPassword());
                editor.commit();

                //Move to the main activity and show all data needed for client
                Intent intent = new Intent(context, MainScreenActivity.class);
                intent.putExtra("user", currentUser);
                context.startActivity(intent);
            } break;
            case REGISTER: {
                ((Activity) context).finish();
            } break;
        }
    }
}
