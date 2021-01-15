package com.example.v2prototypeapp;

import java.util.ArrayList;

public class UsersList {

    private static UsersList userlistinstance = null;
    private ArrayList<Users> userslist;

    private UsersList(){
        userslist = new ArrayList<Users>();
    }

    public static UsersList getInstance(){
        if(userlistinstance == null){
            userlistinstance = new UsersList();
        }
        return userlistinstance;
    }

    public ArrayList<Users> getUserslist() {
        return userslist;
    }

    public void setUsersList(ArrayList<Users> userslist){
        this.userslist = userslist;
    }
}
