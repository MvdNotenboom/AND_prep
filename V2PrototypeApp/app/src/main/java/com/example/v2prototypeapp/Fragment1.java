package com.example.v2prototypeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class Fragment1 extends Fragment {

    private FirebaseAuth firebaseauth;

    ImageView iv_currentuser_avatar;
    TextView tv_currentuser_uid;
    TextView tv_currentuser_name;
    TextView tv_currentuser_email;
    CurrentUser currentuser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        firebaseauth = FirebaseAuth.getInstance();
//
//        iv_currentuser_avatar = getView().findViewById(R.id.iv_currentuser_avatar);
//        tv_currentuser_uid = getView().findViewById(R.id.tv_currentuser_uid);
//        tv_currentuser_name = getView().findViewById(R.id.tv_currentuser_name);
//        tv_currentuser_email = getView().findViewById(R.id.tv_currentuser_email);
//
//        currentuser = CurrentUser.getInstance(getActivity().getApplicationContext());
//        if(currentuser.getUid() != null){
//            tv_currentuser_uid.setText(currentuser.getUid());
//        }
//        if(currentuser.getName() != null){
//            tv_currentuser_name.setText(currentuser.getName());
//        }
//        if(currentuser.getEmail() != null){
//            tv_currentuser_email.setText(currentuser.getEmail());
//        }

        return inflater.inflate(R.layout.fragment1_layout, container, false);
    }



}
