package com.example.javaclient.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClientHandler extends AsyncTask<String, Void, ResponseFormat> {

    private String address;
    private Client client;
    private Context context;
    private Flags currentFlag;

    public ClientHandler(Context context, String address) {
        this.address = address;
        this.context = context;
        client = null;
    }

    @Override
    protected ResponseFormat doInBackground(String... voids) {
        Flags flag = Flags.valueOf(voids[0].toUpperCase());
        String secondArg = voids[1];
        currentFlag = flag;
        client = new Client(address);
        ResponseFormat response = client.ExecCommand(flag, secondArg);
        return response;
    }

    @Override
    protected void onPostExecute(ResponseFormat response) {
        com.example.javaclient.utils.Status status = response.status;
        if(status.equals(com.example.javaclient.utils.Status.OK)) {
            Toast.makeText(context, currentFlag.getMessage(), Toast.LENGTH_SHORT).show();
        } else if(status.equals(com.example.javaclient.utils.Status.NOTFOUND)) {
            Toast.makeText(context, "Not found the one you looking for", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "There seems to be an error connection!", Toast.LENGTH_SHORT).show();
        }
    }
}
