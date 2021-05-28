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

public class ClientHandler extends AsyncTask<String, Void, ResponseFormat> {

    private static ClientHandler clientHandler;
    public static boolean canRun = true;
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

    private void resetClientHandler() {
        if (clientHandler != null) {
            clientHandler = null;
        }
        Context context = this.context;
        clientHandler = ClientHandler.getInstance();
        this.context = context;
        canRun = true;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void startListening() {
        messageThread = new Thread(new Runnable() {

            String data;
            Message message;
            Message[] messages;
            String[] contacts;
            String jsonBody;
            Handler handler = new Handler();

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        try {
                            jsonBody = Client.getInstance().getInputStream().readUTF();
                            System.out.println("BODY JSON!!!!!: " + jsonBody);
                            try {
                                data = Client.getInstance().getGson().fromJson(jsonBody, ResponseFormat.class).data;
                                message = Client.getInstance().getGson().fromJson(jsonBody, Message.class);
                                if (message.getMessage() == null) {
                                    if (data.equals("bye")){
                                        ((MainScreenActivity)context).finish();
                                        System.exit(0);
                                    }
                                    try {
                                        contacts = Client.getInstance().getGson().fromJson(data, String[].class);
                                    } catch (Exception e) {
                                        messages = Client.getInstance().getGson().fromJson(data, Message[].class);
                                    }
                                }
                                if (message.getMessage() != null || messages != null || contacts != null) {
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (message.getMessage() != null) {
                                                if (message.getMessage().msgPurpose.equals(MessagePurpose.BROADCAST)) {
                                                    if (!message.getMessage().getSender().equals(User.getApplicationUser().getUsername()))
                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Broadcast From " + message.getMessage().getSender())
                                                                .setMessage(message.getMessage().content)
                                                                .setPositiveButton("OK", null).show();
                                                } else {
                                                    if (context instanceof ChatActivity) {
                                                        if (((ChatActivity) context).getUser().equals(message.getMessage().getSender()))
                                                            ((ChatActivity) context).updateChat(message);
                                                        else {
                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Message From " + message.getMessage().getSender())
                                                                    .setMessage(message.getMessage().content)
                                                                    .setPositiveButton("OK", null).show();
                                                            if (context instanceof MainScreenActivity)
                                                                ((MainScreenActivity) context).addContactsIfNeeded(message.getMessage().getSender());
                                                        }
                                                    } else {
                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Message From " + message.getMessage().getSender())
                                                                .setMessage(message.getMessage().content)
                                                                .setPositiveButton("OK", null).show();
                                                    }
                                                }
                                            } else if (messages != null) {
                                                if (context instanceof ChatActivity)
                                                    ((ChatActivity) context).updateChat(messages);
                                            } else {
                                                if (context instanceof MainScreenActivity)
                                                    ((MainScreenActivity) context).updateContacts(contacts);
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
                    requestFormat = new RequestFormat(Commends.MESSAGE, Client.getInstance().getGson().toJson(messagePacket));
                    responseFormat = Client.getInstance().execCommand(Commends.MESSAGE,
                            Client.getInstance().getGson().toJson(requestFormat));
                    System.out.println(Client.getInstance().getGson().toJson(messagePacket) + "");
                }
            }
        });

        sendingThread.start();
    }

    @Override
    protected ResponseFormat doInBackground(String... voids) {
        canRun = false;
        Commends flag = Commends.valueOf(voids[0].toUpperCase());
        currentFlag = flag;
        String secondArg = voids[1];
        if (flag.equals(Commends.LOGIN) || flag.equals(Commends.REGISTER)) {
            String username = secondArg.split(" ")[0];
            String password = secondArg.split(" ")[1];
            User user = new User(username, password);
            User.setApplicationUser(user);
        }
        Client.getInstance();
        ResponseFormat response = Client.getInstance().execCommand(flag, secondArg);
        return response;
    }

    @Override
    protected void onPostExecute(ResponseFormat response) {
        resetClientHandler();
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
