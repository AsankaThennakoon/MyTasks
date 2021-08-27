package com.industrialmaster.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTasks(View view) {
        startActivity(new Intent(this,AddTasks.class));
    }

    public void viewTasks(View view) {

        startActivity(new Intent(this,ViewTasks.class));
    }

    public void editProfile(View view) {

        startActivity(new Intent(this,EditProfile.class));
    }
}