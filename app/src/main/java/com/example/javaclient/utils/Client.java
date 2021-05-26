package com.example.javaclient.utils;// A Java program for a Client

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

public class Client {
    // initialize socket and input output streams
    private final String SERVER_IP = "192.168.14.84";
    static int PORT = 7800;
    private Socket socket;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    private Gson gson;
    private static Client client = null;

    // constructor to put ip address and port
    private Client() {
        try {
            socket = new Socket(SERVER_IP, PORT);
            System.out.println("Connected...");

            // receive from the socket
            inStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            // sends to the socket
            outStream = new DataOutputStream(socket.getOutputStream());

            gson = new Gson();
        } catch (IOException i) {
            System.out.println(i);
            System.out.println("Can not reach the server");
        }
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public Gson getGson() {
        return gson;
    }

    public DataInputStream getInputStream() {
        return this.inStream;
    }

    private void closeSocket(String msg) {

        System.out.println(msg);
        System.out.println("Closing Connection...");

        try {
            this.socket.close();
            this.outStream.close();
            this.inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public ResponseFormat execCommand(Commends command, String data) {

        if (command.equals(Commends.MESSAGE))
            message(data);
        if (command.equals(Commends.CREATE) || command.equals(Commends.REGISTER))
            return createUser(data);
        if (command.equals(Commends.LOGIN))
            return login(data);
        if (command.equals(Commends.LOAD_MESSAGES))
            getChatMessages(data);

        return null;
    }

    private void getChatMessages(String data) {

        RequestFormat req = new RequestFormat(Commends.LOAD_MESSAGES, data);
        String request = gson.toJson(req);

        try {
            this.outStream.writeUTF(request);
        } catch (SocketException i) {
            this.closeSocket("Server forced to shutdown");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void message(String data) {
        try {
            outStream.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ResponseFormat createUser(String data) {

        // get user
        String user = userJsonFromString(data);

        RequestFormat req = new RequestFormat(Commends.CREATE, user);
        String request = gson.toJson(req);

        try {
            this.outStream.writeUTF(request);
            String response = inStream.readUTF();
            ResponseFormat res = gson.fromJson(response, ResponseFormat.class);

            return res;
        } catch (SocketException i) {
            this.closeSocket("Server forced to shutdown");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ResponseFormat login(String data) {

        // get user
        String user = userJsonFromString(data);

        RequestFormat req = new RequestFormat(Commends.LOGIN, user);
        String request = gson.toJson(req);

        try {
            this.outStream.writeUTF(request);
            String response = inStream.readUTF();
            ResponseFormat res = gson.fromJson(response, ResponseFormat.class);
            return res;
        } catch (SocketException i) {
            this.closeSocket("Server forced to shutdown");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String userJsonFromString(String data) {
        int index = data.indexOf(' ');
        String username = data.substring(0, index);
        String password = data.substring(index + 1);

        String encryptedPass = "";
        try {
            encryptedPass = Encryptor.encryptPass(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(username, encryptedPass);
        return gson.toJson(user);
    }
}