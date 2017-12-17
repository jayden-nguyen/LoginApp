package com.example.asus.loginapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.loginapp.CreateAsk;
import com.example.asus.loginapp.R;

public class MainCommunity extends AppCompatActivity {
    TextView home, profile;
    Button create_ask_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_community);

        home = (TextView) findViewById(R.id.home);
        profile = (TextView)findViewById(R.id.profile);
        create_ask_button = (Button)findViewById(R.id.create_ask_button);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCommunity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCommunity.this,ProfileUserActivity.class);
                startActivity(intent);
            }
        });
        create_ask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCommunity.this,CreateAsk.class);
                startActivity(intent);
            }
        });

    }


}
