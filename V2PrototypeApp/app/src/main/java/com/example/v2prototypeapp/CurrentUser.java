package com.example.v2prototypeapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CurrentUser {

    private static CurrentUser currentuser = null;
    private FirebaseUser fireuser;
    private String uid;
    private String name;
    private String email;

    private CurrentUser(FirebaseUser fireuser){
        this.fireuser = fireuser;
        uid = "uid comes here"; //fireuser.getUid();
        name = "no information on name found";
        email = "no information on email found";
    }
    public static CurrentUser getInstance(FirebaseUser fireuser){
        if(currentuser == null){
            currentuser = new CurrentUser(fireuser);
        }
        return currentuser;
    }

    private CurrentUser(){
        uid = "UID_dummy_value";
        name = "NAME_dummy_value";
        email = "EMAIL_dummy_value";
    }
    public static CurrentUser getInstance(Context c){
        if(currentuser == null){
            currentuser = new CurrentUser();
            Toast.makeText(c, "WARNING!_EMPTY_CURRENTUSER_CREATED", Toast.LENGTH_LONG).show();
        }
        return currentuser;
    }

    public void clear(){
        currentuser = null;
        fireuser = null;
        uid = null;
        name = null;
        email = null;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FirebaseUser getFireuser() {
        return fireuser;
    }

    public void setFireuser(FirebaseUser fireuser) {
        this.fireuser = fireuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
