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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    EditText et_register_name;
    EditText et_register_email;
    EditText et_register_password;
    Button btn_register_register;
    Button btn_register_cancel;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        et_register_name = findViewById(R.id.et_register_name);
        et_register_email = findViewById(R.id.et_register_email);
        et_register_password = findViewById(R.id.et_register_password);
        btn_register_register = findViewById(R.id.btn_register_register);
        btn_register_cancel = findViewById(R.id.btn_register_cancel);

        firebaseauth = FirebaseAuth.getInstance();

        btn_register_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginLaunchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.swipe_in_left,R.anim.swipe_out_left);
            }
        });
        btn_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_register_email.getText().toString().trim();
                String password = et_register_password.getText().toString().trim();

                firebaseauth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    FirebaseUser fireuser = firebaseauth.getCurrentUser();
                                    CurrentUser.getInstance(fireuser);
                                    Toast.makeText(RegisterUser.this, "Registration successfull", Toast.LENGTH_LONG).show();
                                    uploadUsertofirebase(fireuser);
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterUser.this, "Failure to create user try again or mayby you already have an account", Toast.LENGTH_LONG).show();

                                }
                            }
                        });

            }
        });

    }

    private void uploadUsertofirebase(FirebaseUser fireuser) {
        Users newuser = new Users(fireuser.getUid(), et_register_name.getText().toString().trim(), et_register_email.getText().toString().trim());

        CurrentUser currentuser = CurrentUser.getInstance(fireuser);
        if(fireuser.getUid().equals(currentuser.getUid())){
            currentuser.setName(et_register_name.getText().toString().trim());
            currentuser.setEmail(et_register_email.getText().toString().trim());
        }else{
            Toast.makeText(this, "WARNING!_FAULT_UID_MISMATCH_FIREUSER_CURRENTUSER", Toast.LENGTH_SHORT).show();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("users");

        myref.child(newuser.getId()).setValue(newuser);

    }
}