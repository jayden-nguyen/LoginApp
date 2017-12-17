package com.example.asus.loginapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.asus.loginapp.DownDealResultTask;
import com.example.asus.loginapp.R;

public class History extends AppCompatActivity {
    TextView home,profile,community;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        DownDealResultTask downDealResultTask = new DownDealResultTask(History.this);
        downDealResultTask.execute();
        home = (TextView)findViewById(R.id.home);
        profile = (TextView)findViewById(R.id.profile);
        community = (TextView)findViewById(R.id.community);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this,ProfileUserActivity.class);
                startActivity(intent);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this,MainCommunity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
