package com.example.v2prototypeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CurrentUserProfile extends AppCompatActivity {

    private float touchX, touchY, touchX1, touchY1;

    private FirebaseAuth firebaseauth;

    ImageView iv_currentuser_avatar;
    TextView tv_currentuser_uid;
    TextView tv_currentuser_name;
    TextView tv_currentuser_email;
    CurrentUser currentuser;

    //ArrayList<Users> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_profile);

        //UsersList users = Users.getInstance();

        firebaseauth = FirebaseAuth.getInstance();

        iv_currentuser_avatar = findViewById(R.id.iv_currentuser_avatar);
        tv_currentuser_uid = findViewById(R.id.tv_currentuser_uid);
        tv_currentuser_name = findViewById(R.id.tv_currentuser_name);
        tv_currentuser_email = findViewById(R.id.tv_currentuser_email);

        currentuser = CurrentUser.getInstance(this);
        if(currentuser.getUid() != null){
            tv_currentuser_uid.setText(currentuser.getUid());
        }
        if(currentuser.getName() != null){
            tv_currentuser_name.setText(currentuser.getName());
        }
        if(currentuser.getEmail() != null){
            tv_currentuser_email.setText(currentuser.getEmail());
        }

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
//                    Intent intent = new Intent(this, SwipeLeft.class);
//                    startActivity(intent);
                }else if(touchX > touchX1){
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.swipe_in_right,R.anim.swipe_out_right);
                }
                break;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.swipe_in_right,R.anim.swipe_out_right);
    }
}