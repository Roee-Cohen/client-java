package com.example.javaclient.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.javaclient.activities.ChatActivity;
import com.example.javaclient.activities.MainScreenActivity;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;

public class ClientHandler extends AsyncTask<String, Void, ResponseFormat> {

    private static ClientHandler clientHandler;
    private Client client;
    private Context context;
    private Commends currentFlag;

    private Thread messageThread;
    private Thread sendingThread;

    private ClientHandler() {
    }

    public static ClientHandler getInstance() {
        if (clientHandler == null)
            clientHandler = new ClientHandler();
        return clientHandler;
    }

//    private void resetClientHandler() {
//        if (clientHandler != null)
//            clientHandler.cancel(true);
//        clientHandler = ClientHandler.getInstance();
//        clientHandler.setContext(context);
//        clientHandler.setClient(client);
//        clientHandler.setCurrentUser(User.getApplicationUser());
//    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void stopListening() {
        if (messageThread != null)
            messageThread.stop();
    }

    public void startListening() {
        messageThread = new Thread(new Runnable() {

            String data;
            MessagePacket responseMessagePacket;
            Message[] messages;
            String jsonBody;
            Handler handler = new Handler();

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        try {
                            jsonBody = client.getInputStream().readUTF();
                            System.out.println("BODY JSON!!!!!: " + jsonBody);
                            try {
                                data = client.getGson().fromJson(jsonBody, ResponseFormat.class).data;
                                responseMessagePacket = client.getGson().fromJson(jsonBody, Message.class).getMsg();
                                if (responseMessagePacket == null) {
                                    messages = client.getGson().fromJson(data, Message[].class);
                                }
                                if ((responseMessagePacket != null && responseMessagePacket.content != null
                                        && responseMessagePacket.content != "null") || messages != null) {
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (responseMessagePacket != null) {
                                                if (responseMessagePacket.msgPurpose.equals(MessagePurpose.BROADCAST)) {
                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Broadcast")
                                                            .setMessage(responseMessagePacket.content)
                                                            .setPositiveButton("OK", null).show();
                                                } else {
                                                    if (context instanceof ChatActivity)
                                                        if (((ChatActivity) context).getUser().getUsername().equals(responseMessagePacket.getSender()))
                                                            ((ChatActivity) context).updateChat(responseMessagePacket);
                                                        else
                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Message From " + responseMessagePacket.getSender())
                                                                    .setMessage(responseMessagePacket.content)
                                                                    .setPositiveButton("OK", null).show();
                                                }
                                            } else {
                                                if (context instanceof ChatActivity)
                                                    ((ChatActivity) context).updateChat(messages);
                                            }
                                        }
                                    }, 500);
                                }
                            } catch (JsonSyntaxException e) {
                                System.out.println("JSON BODY: GOT SOMETHING else!!!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        messageThread.start();
    }

    public void sendMessage(final String message) {
        sendingThread = new Thread(new Runnable() {

            MessagePacket messagePacket;
            ResponseFormat responseFormat;
            RequestFormat requestFormat;

            @Override
            public void run() {
                synchronized (this) {
                    messagePacket = new MessagePacket(User.getApplicationUser().getUsername(), message, "evreybody",
                            MessageType.MESSAGE, MessagePurpose.BROADCAST);
                    requestFormat = new RequestFormat(Commends.MESSAGE, client.getGson().toJson(messagePacket));
                    responseFormat = client.execCommand(Commends.MESSAGE, client.getGson().toJson(requestFormat));
                    System.out.println(client.getGson().toJson(messagePacket) + "");
//                    System.out.println("Data: " + responseFormat.data);
                }
            }
        });

        sendingThread.start();
    }

    public void createNewChat(String sender, String destination) {

    }

    @Override
    protected ResponseFormat doInBackground(String... voids) {
        Commends flag = Commends.valueOf(voids[0].toUpperCase());
        currentFlag = flag;
        String secondArg = voids[1];
        if (flag.equals(Commends.LOGIN) || flag.equals(Commends.REGISTER)) {
            String username = secondArg.split(" ")[0];
            String password = secondArg.split(" ")[1];
            User user = new User(username, password);
            User.setApplicationUser(user);
        }
        client = Client.getInstance();
        ResponseFormat response = client.execCommand(flag, secondArg);
        return response;
    }

    @Override
    protected void onPostExecute(ResponseFormat response) {
        com.example.javaclient.utils.Status status = response.status;
        if (status.equals(com.example.javaclient.utils.Status.OK)) {
            Toast.makeText(context, currentFlag.getMessage(), Toast.LENGTH_SHORT).show();
            makeAction(currentFlag);
        } else if (status.equals(com.example.javaclient.utils.Status.NOTFOUND)) {
            Toast.makeText(context, "User not found!", Toast.LENGTH_SHORT).show();
        } else if (status.equals(com.example.javaclient.utils.Status.BAD_REQUEST)) {
            Toast.makeText(context, "User already exists!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "There seems to be an error connection!", Toast.LENGTH_SHORT).show();
        }

//        resetClientHandler();
    }

    private void makeAction(Commends flag) {
        switch (flag) {
            case LOGIN: {
                //Save data to shared preferences
                SharedPreferences storage = context.getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("username", User.getApplicationUser().getUsername());
                editor.putString("password", User.getApplicationUser().getPassword());
                editor.commit();

                //Move to the main activity and show all data needed for client
                Intent intent = new Intent(context, MainScreenActivity.class);
                context.startActivity(intent);
            }
            break;
            case REGISTER: {
            }
            break;
        }
        ((Activity) context).finish();
    }
}
