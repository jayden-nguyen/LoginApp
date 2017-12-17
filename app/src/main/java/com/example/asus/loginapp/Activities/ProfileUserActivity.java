package com.example.asus.loginapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.loginapp.LoadProfileUserTask;
import com.example.asus.loginapp.ProductProfile;
import com.example.asus.loginapp.R;

public class ProfileUserActivity extends AppCompatActivity {
    TextView home,profile,community,history,product_profile;
    Button update_user_button;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
            Log.d("Welcome to profile user","page");
        // TODO: Load infomation from database
            LoadProfileUserTask loadProfileUserTask = new LoadProfileUserTask(ProfileUserActivity.this);
            Log.d("the program stop here 1","MMM");
            loadProfileUserTask.execute();
        home = (TextView) findViewById(R.id.home);
        community = (TextView) findViewById(R.id.community);
        history = (TextView) findViewById(R.id.history);
        product_profile = (TextView) findViewById(R.id.product_profile);
        update_user_button = (Button)findViewById(R.id.update_user_button);
        /**
         * Intent to other activity
         */
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUserActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUserActivity.this,MainCommunity.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUserActivity.this,History.class);
                startActivity(intent);
            }
        });
        product_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUserActivity.this,ProductProfile.class);
                startActivity(intent);
            }
        });
        update_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUserActivity.this,UpdateProfileUser.class);
                startActivity(intent);
            }
        });
    }
}
