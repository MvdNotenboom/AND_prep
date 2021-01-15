package com.example.v2prototypeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_send;
    TextView et_get;
    Button button_send;
    Button button_get;
    Button button_cycle;
    TextView tvpos;
    TextView tvlenght;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_get = (Button)findViewById(R.id.button_get);
        button_send = findViewById(R.id.button_send);
        et_get = findViewById(R.id.et_get);
        et_send = findViewById(R.id.et_send);
        button_cycle = findViewById(R.id.button_cycle);
        tvlenght = findViewById(R.id.tvlenght);
        tvpos = findViewById(R.id.tvpos);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_get.setText("send button");
                String id = myref.push().getKey();
                Users user = new Users(id, et_send.getText().toString().trim(), "hello");
                myref.child(id).setValue(user);
            }
        });

        button_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        button_cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCycle();
            }
        });

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref = database.getReference("users");
    //ArrayList<Users> userlist = new ArrayList<>();
    UsersList userlistinstance = UsersList.getInstance();
    ArrayList<Users> userlist = userlistinstance.getUserslist();
    int pos = 0;
    int pos2 = 0;

    public void getData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> items = snapshot.getChildren().iterator();
                userlistinstance.getUserslist().clear();

                while(items.hasNext()){
                    DataSnapshot item = items.next();
                    Users user = item.getValue(Users.class);
                    userlistinstance.getUserslist().add(user);

                    String value = userlistinstance.getUserslist().get(0).getName();
                    et_get.setText(value);
                    myref.removeEventListener(this);
                }
                Log.e("TAG", Integer.toString(userlistinstance.getUserslist().size()));
                Log.e("TAG", userlistinstance.getUserslist().get(pos).getName());
                pos++;
                //String value = snapshot.child("exampleuser").getValue(String.class);
                //et_get.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void dataCycle() {
        et_get.setText(userlist.get(pos2).getName());
        tvlenght.setText(userlist.size()+"");
        tvpos.setText(pos2+"");
        if(pos2+1 < userlist.size()) {
            pos2++;
        }else{
            pos2 = 0;
        }
    }




}