package com.example.myapplication.Models;

public class CurrentUser {

    private static CurrentUser instance;
    private User user;

    private CurrentUser(){
    }

    public static CurrentUser getInstance(){
        instance = (instance == null) ? new CurrentUser(): instance;
        return instance;
    }

    public void setUser(User u){
        user = u;
    }

    public User getUser(){
        return user;
    }

}
