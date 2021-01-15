package com.example.v2prototypeapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.v2prototypeapp.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(1);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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


}