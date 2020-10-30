package com.example.javaclient.utils;// A Java program for a Client

import android.os.AsyncTask;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

public class Client
{
    // initialize socket and input output streams
    static int PORT = 7800;
    private Socket socket;
    private String address;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    private Gson g;

    // constructor to put ip address and port
    public Client(String address)
    {
        this.address = address;

        try
        {
            this.socket = new Socket(address, PORT);
            System.out.println("Connected...");

            // receive from the socket
            this.inStream = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));

            // sends to the socket
            this.outStream = new DataOutputStream(this.socket.getOutputStream());

            this.g = new Gson();
        }
        catch(IOException i)
        {
            System.out.println(i);
            System.out.println("Can not reach the server");
        }
    }

    public Gson getG() {
        return g;
    }

    public DataInputStream getInputStream() {
        return this.inStream;
    }


    private void closeSocket(String msg){

        System.out.println(msg);
        System.out.println("Closing Connection...");

        try {
            this.socket.close();
            this.outStream.close();
            this.inStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public ResponseFormat ExecCommand(Flags command, String data){

        if(command.equals(Flags.MESSAGE)) {
            return message(data);
        }
        if (command.equals(Flags.CREATE) || command.equals(Flags.REGISTER))
            return createUser(data);
        if (command.equals(Flags.LOGIN))
            return Login(data);

        return null;
    }

    private ResponseFormat message(String data) {
        ResponseFormat response;
        try {
            this.outStream.writeUTF(data);
            response = new ResponseFormat(Status.OK, this.inStream.readUTF());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseFormat createUser(String data){

        // get user
        String user = this.userJsonFromString(data);

        RequestFormat req = new RequestFormat(Flags.CREATE, user);
        String request = this.g.toJson(req);

        try {
            this.outStream.writeUTF(request);
            String response = this.inStream.readUTF();
            ResponseFormat res = this.g.fromJson(response, ResponseFormat.class);

            return res;
        }
        catch (SocketException i) { this.closeSocket("Server forced to shutdown"); }
        catch (IOException e) { e.printStackTrace(); }

        return null;
    }

    private ResponseFormat Login(String data){

        // get user
        String user = this.userJsonFromString(data);

        RequestFormat req = new RequestFormat(Flags.LOGIN, user);
        String request = this.g.toJson(req);

        try {
            this.outStream.writeUTF(request);
            String response = this.inStream.readUTF();
            ResponseFormat res = this.g.fromJson(response, ResponseFormat.class);
            return res;
        }
        catch (SocketException i) { this.closeSocket("Server forced to shutdown"); }
        catch (IOException e) { e.printStackTrace(); }

        return null;
    }

    private String userJsonFromString(String data){
        int index = data.indexOf(' ');
        String username = data.substring(0, index);
        String password = data.substring(index + 1);

        String encryptedPass = "";
        try { encryptedPass = Encryptor.encryptPass(password); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        User u = new User(username, encryptedPass);
        return this.g.toJson(u);
    }
}