package com.example.v2prototypeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import android.view.View.OnTouchListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Home extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref = database.getReference("users");
    UsersList userlistinstance = UsersList.getInstance();
    ArrayList<Users> userlist = userlistinstance.getUserslist();

    private FirebaseAuth firebaseauth;
    private float touchX, touchY, touchX1, touchY1;
//    UserAdapterRv adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseauth = FirebaseAuth.getInstance();

        getData();
        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        ArrayList<Users> list = userlistinstance.getUserslist();

//        // set up the RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.home_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new UserAdapterRv(this, list);
//        //adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);


    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX = touchEvent.getX();
                touchY = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchX1 = touchEvent.getX();
                touchY1 = touchEvent.getY();
                if(touchX < touchX1){
                Intent i = new Intent(this, CurrentUserProfile.class);
                startActivity(i);
                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_out_left);
            }else if(touchX > touchX1){
//                Intent intent = new Intent(this, SwipeRight.class);
//                startActivity(intent);
            }
            break;
        }
        return true;
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
                }else {
                    CurrentUser.getInstance(this).clear();
                    firebaseauth.signOut();
                    Intent intent = new Intent(this, LoginLaunchActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.menu_profile:
//                if(firebaseauth.getCurrentUser()==null){
//                    Toast.makeText(this, "No logged in user found please do a cold boot", Toast.LENGTH_LONG).show();
//                }else {
//                    CurrentUser.getInstance(this).clear();
//                    firebaseauth.signOut();
                Intent intent = new Intent(this, CurrentUserProfile.class);
                startActivity(intent);
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> items = snapshot.getChildren().iterator();
                userlistinstance.getUserslist().clear();
                ArrayList<Users> list = userlistinstance.getUserslist();

                while(items.hasNext()){
                    DataSnapshot item = items.next();
                    Users user = item.getValue(Users.class);
                    Log.e("TAGG!!!!!!!!", user.getName());
                    list.add(user);

                    //String value = userlistinstance.getUserslist().get(0).getName();
                    //et_get.setText(value);

                }
                userlistinstance.setUsersList(list);
                Log.e("TAG", Integer.toString(userlistinstance.getUserslist().size()));
                myref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}