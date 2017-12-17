package com.example.asus.loginapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.loginapp.R;
import com.example.asus.loginapp.UpdateUserTask;

public class UpdateProfileUser extends AppCompatActivity {
    EditText update_user_name,update_email,update_password,update_DOB,update_rating,update_age,update_gender,update_full_name;
    Button update_user_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_user);

        //Match
        update_user_name = (EditText)findViewById(R.id.update_user_name);
        update_email = (EditText)findViewById(R.id.update_email);
        update_password = (EditText)findViewById(R.id.update_password);
        update_DOB = (EditText)findViewById(R.id.update_DOB);
        update_full_name = (EditText)findViewById(R.id.update_fullname);
        update_gender = (EditText)findViewById(R.id.update_gender);
        update_rating = (EditText)findViewById(R.id.update_user_rating);
        update_age = (EditText)findViewById(R.id.update_user_age);
        Log.d("hello welcome","to updateuser");
        update_user_button = (Button)findViewById(R.id.update_user_button);
        //CLick event
        update_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Start update"," OK");
                UpdateUserTask updateUserTask = new UpdateUserTask(UpdateProfileUser.this);
                updateUserTask.execute(update_user_name.getText().toString(),update_email.getText().toString()
                        ,update_password.getText().toString(),
                        update_full_name.getText().toString(),update_age.getText().toString(),
                        update_DOB.getText().toString(),update_gender.getText().toString(),update_rating.getText().toString());
            }
        });

    }
}
