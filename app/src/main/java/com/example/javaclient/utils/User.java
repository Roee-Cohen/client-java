package com.example.javaclient.utils;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private static User applicationUser;


    public static User getApplicationUser() {
        return applicationUser;
    }

    public static void setApplicationUser(User applicationUser) {
        User.applicationUser = applicationUser;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
