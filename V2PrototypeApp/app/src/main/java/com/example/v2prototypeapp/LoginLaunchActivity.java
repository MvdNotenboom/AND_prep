package com.example.v2prototypeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginLaunchActivity extends AppCompatActivity {

    EditText et_launch_email;
    EditText et_launch_password;
    Button btn_launch_singin;
    Button btn_launch_registeruser;
    Button btn_launch_totest;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_launch);

        et_launch_email = findViewById(R.id.et_launch_email);
        et_launch_password = findViewById(R.id.et_launch_password);
        btn_launch_singin = findViewById(R.id.btn_launch_signin);
        btn_launch_registeruser = findViewById(R.id.btn_launch_registeruser);
        btn_launch_totest = findViewById(R.id.btn_launch_totest);

        firebaseauth = FirebaseAuth.getInstance();


        btn_launch_totest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        btn_launch_singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //temporary for ease of testing
                CurrentUser currentuser = CurrentUser.getInstance(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);

//                if(et_launch_email.getText().toString().trim().isEmpty()) {
//                    et_launch_email.setError("ERROR_Can_not_be_empty");
//                    et_launch_email.requestFocus();
//                }else if(!isEmailValid(et_launch_email.getText().toString().trim())){
//                    et_launch_email.setError("ERROR_Not_valid_email");
//                    et_launch_email.requestFocus();
//                }else if(et_launch_password.getText().toString().trim().isEmpty()){
//                    et_launch_password.setError("ERROR_Can_not_be_empty");
//                    et_launch_password.requestFocus();
//                }else {
//                    String email = et_launch_email.getText().toString().trim();
//                    String password = et_launch_password.getText().toString().trim();
//
//                    firebaseauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginLaunchActivity.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                FirebaseUser fireuser = firebaseauth.getCurrentUser();
//                                setupCurrentUser(fireuser);
//                                Toast.makeText(LoginLaunchActivity.this, "Login succesfull", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(getApplicationContext(), Home.class);
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(LoginLaunchActivity.this, "Login failure, Please check email/password or register", Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//                    });
//                }
            }
        });

        btn_launch_registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.swipe_in_right,R.anim.swipe_out_right);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                if(firebaseauth.getCurrentUser()==null){
                    Toast.makeText(this, "No logged in user found please do a cold boot", Toast.LENGTH_LONG).show();
                    //only for testing logout without user
                    Intent intent = new Intent(this, LoginLaunchActivity.class);
                    startActivity(intent);
                }else {
                    CurrentUser.getInstance(this).clear();
                    firebaseauth.signOut();
                    Intent intent = new Intent(this, LoginLaunchActivity.class);
                    startActivity(intent);
                }
                return true;
//            case R.id.menu_profile:
////                if(firebaseauth.getCurrentUser()==null){
////                    Toast.makeText(this, "No logged in user found please do a cold boot", Toast.LENGTH_LONG).show();
////                }else {
////                    CurrentUser.getInstance(this).clear();
////                    firebaseauth.signOut();
//                    Intent intent = new Intent(this, CurrentUserProfile.class);
//                    startActivity(intent);
////                }
////                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupCurrentUser(FirebaseUser fireuser) {
        CurrentUser currentuser = CurrentUser.getInstance(fireuser);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("users");
        myref.child(fireuser.getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG0", snapshot.getValue(String.class));
                String name = snapshot.getValue(String.class);
                currentuser.setName(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginLaunchActivity.this, "WARNING!-FAILURE-SETTING-CURRENTUSER", Toast.LENGTH_SHORT).show();
            }
        });
        myref.child(fireuser.getUid()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG1", snapshot.getValue(String.class));
                String email = snapshot.getValue(String.class);
                currentuser.setEmail(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginLaunchActivity.this, "WARNING!-FAILURE-SETTING-CURRENTUSER", Toast.LENGTH_SHORT).show();
            }
        });
        //Section of availible data that can be gathered. Need to do something with it.
//        fireuser.getDisplayName();
//        fireuser.getEmail();
//        fireuser.getPhoneNumber();
//        fireuser.getPhotoUrl();
//        fireuser.getUid();
//        fireuser.isEmailVerified();
//        fireuser.sendEmailVerification();
    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}